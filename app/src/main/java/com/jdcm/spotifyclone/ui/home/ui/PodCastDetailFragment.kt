package com.jdcm.spotifyclone.ui.home.ui

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.jdcm.spotifyclone.R
import com.jdcm.spotifyclone.databinding.FragmentPodCastDetailBinding
import com.jdcm.spotifyclone.ui.home.ui.adapter.ChannelDetailAdapter
import com.jdcm.spotifyclone.ui.home.ui.data.model.AudioClips
import com.jdcm.spotifyclone.ui.home.ui.data.model.SongsModel
import com.jdcm.spotifyclone.utils.Constants
import com.jdcm.spotifyclone.utils.rvListener.ItemSongsClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class PodCastDetailFragment : Fragment() {

    private lateinit var binding: FragmentPodCastDetailBinding
    private val viewModel: PodCastDetailViewModel by viewModels()
    private val channelId by lazy { PodCastDetailFragmentArgs.fromBundle(requireArguments()).channelId }
    private var audioClipsList: ArrayList<AudioClips>? = null
    private var adapter: ChannelDetailAdapter? = null
    private val podCast by lazy { actualSongUri }

    private val mediaPlayer by lazy {
        val media = MediaPlayer()
        media.setDataSource(podCast)
        media.prepare()
        media
    }

    private val songs by lazy {
        val allSongs: ArrayList<SongsModel> = ArrayList()
        for (clips in audioClipsList!!) {
            allSongs.add(SongsModel(clips.urls.high_mp3, clips.title))
        }
        allSongs.filter { it.mp3.contains(".mp3") }
    }

    var actualSongIndex = 0
        set(value) {
            val circularVariable = if (value == -1) {
                songs.size - 1
            } else {
                value % songs.size
            }
            field = circularVariable
            actualSongName = songs[circularVariable].songName
            actualSongUri = songs[circularVariable].mp3

        }

    var actualSongName: String = ""
    var actualSongUri: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPodCastDetailBinding.inflate(inflater, container, false)
        binding.toolbarPodCastDetail.setNavigationOnClickListener { findNavController().popBackStack() }
        setUiBasicInfo()
        initViewModel()
        binding.barMusicPlayer.btnImvAction.setOnClickListener(this::playClicked)
        binding.barMusicPlayer.btnImvActionNext.setOnClickListener(this::nextClicked)
        binding.barMusicPlayer.btnImvActionPrevious.setOnClickListener(this::prevClicked)
        return binding.root
    }

    private fun initViewModel() {

        viewModel.onCreate(Constants.API_VERSION_ONE, channelId)

        viewModel.channelDetail.observe(viewLifecycleOwner) { ChannelDetailApi ->
            if (ChannelDetailApi != null) {
                binding.noDataLayout.root.visibility = View.GONE
                binding.relativeNoConnection.visibility = View.GONE

                audioClipsList = ArrayList()
                audioClipsList!!.clear()
                audioClipsList!!.addAll(ChannelDetailApi.audio_clips)
                initRecyclerView(binding.rvPodcastEpisodes)
                initMediaPlayer()
            } else {
                //If theres no connection de no data layout pops and make the Intent to go to wifi settings
                binding.noDataLayout.root.visibility = View.VISIBLE
                binding.relativeNoConnection.visibility = View.VISIBLE
                binding.noDataLayout.btnReconnection.setOnClickListener {
                    startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                    viewModel.onCreate(Constants.API_VERSION_TWO, channelId)
                }
            }

        }

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it

            binding.podcastImv.isVisible = !it

            binding.podcastTitle.isVisible = !it

            binding.descriptionTv.isVisible = !it

            binding.allEpisodesSubtitle.isVisible = !it
        }

    }

    private fun initMediaPlayer() {
        actualSongName = songs[actualSongIndex].songName
        actualSongUri = songs[actualSongIndex].mp3
        binding.barMusicPlayer.podcastTitle.text = actualSongName
    }

    private fun setUiBasicInfo() {
        //Podcast Logo
        Glide.with(requireContext())
            .load(PodCastDetailFragmentArgs.fromBundle(requireArguments()).channelLogo)
            .placeholder(R.drawable.ic_podcast)
            .into(binding.podcastImv)

        Glide.with(requireContext())
            .load(PodCastDetailFragmentArgs.fromBundle(requireArguments()).channelLogo)
            .placeholder(R.drawable.ic_podcast)
            .into(binding.barMusicPlayer.podcastImv)
        //PodCast Title
        binding.podcastTitle.text =
            PodCastDetailFragmentArgs.fromBundle(requireArguments()).channelTitle
        binding.toolbarTitle.text =
            PodCastDetailFragmentArgs.fromBundle(requireArguments()).channelTitle
        //To observe the state of collapsing toolbar
        binding.appBarLayout.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                //Collapsed
                binding.toolbarTitle.visibility = View.VISIBLE
            } else {
                binding.toolbarTitle.visibility = View.GONE
            }
        })
        //PodcastDescription
        binding.descriptionTv.text =
            PodCastDetailFragmentArgs.fromBundle(requireArguments()).channelDescription
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.hasFixedSize()
        adapter = ChannelDetailAdapter(
            audioClipsList!!,
            requireActivity(),
            object : ItemSongsClickListener {
                override fun onClickPlay(position: Int) {
                    actualSongIndex = position
                    refreshSong()
                    adapter!!.notifyDataSetChanged()
                }
            })
        recyclerView.adapter = adapter
    }

    private fun playClicked(v: View) {

        if (!mediaPlayer.isPlaying) {
            binding.barMusicPlayer.root.visibility = View.VISIBLE
            binding.barMusicPlayer.btnImvAction.setImageDrawable(requireContext().getDrawable(R.drawable.ic_pause))
            CoroutineScope(Dispatchers.IO).launch {
                mediaPlayer.start()
            }
        } else {
            binding.barMusicPlayer.btnImvAction.setImageDrawable(requireContext().getDrawable(R.drawable.ic_play))
            CoroutineScope(Dispatchers.IO).launch {

                mediaPlayer.pause()
            }
        }
    }

    private fun nextClicked(v: View) {
        actualSongIndex++
        refreshSong()
    }

    private fun prevClicked(v: View) {
        actualSongIndex--
        refreshSong()
    }

    fun refreshSong() {

        CoroutineScope(Dispatchers.IO).launch {
            mediaPlayer.reset()
        }
        val uri = actualSongUri
        mediaPlayer.setDataSource(uri)
        mediaPlayer.prepare()

        playClicked(binding.barMusicPlayer.btnImvAction)
        binding.barMusicPlayer.podcastTitle.text = actualSongName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        CoroutineScope(Dispatchers.IO).launch {
            mediaPlayer.stop()
        }
    }

}