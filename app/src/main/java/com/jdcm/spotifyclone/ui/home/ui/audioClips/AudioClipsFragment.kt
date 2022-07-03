package com.jdcm.spotifyclone.ui.home.ui.audioClips

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.jdcm.spotifyclone.R
import com.jdcm.spotifyclone.databinding.FragmentAudioClipsBinding
import com.jdcm.spotifyclone.ui.home.ui.audioClips.adapter.AudioClipsAdapter
import com.jdcm.spotifyclone.ui.home.ui.audioClips.data.model.AudioClips
import com.jdcm.spotifyclone.ui.home.ui.channelDetail.data.model.SongsModel
import com.jdcm.spotifyclone.utils.Constants
import com.jdcm.spotifyclone.utils.rvListener.ItemSongsClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class AudioClipsFragment : Fragment() {

    private lateinit var binding : FragmentAudioClipsBinding
    private val viewModel: AudioClipsViewModel by viewModels()
    private var adapter: AudioClipsAdapter? = null
    private lateinit var audioClipsList : ArrayList<AudioClips>

    private val audioClip by lazy { actualSongUri }

    private val mediaPlayer by lazy {
        val media = MediaPlayer()
        media.setDataSource(audioClip)
        media.prepareAsync()
        media
    }

    private val audios by lazy {
        val allSongs: ArrayList<SongsModel> = ArrayList()
        for (clips in audioClipsList) {
            allSongs.add(SongsModel(clips.urls.high_mp3, clips.title))
        }
        allSongs.filter { it.mp3.contains(".mp3") }
    }

    var actualSongIndex = 0
        set(value) {
            val circularVariable = if (value == -1) {
                audios.size - 1
            } else {
                value % audios.size
            }
            field = circularVariable
            actualSongName = audios[circularVariable].songName
            actualSongUri = audios[circularVariable].mp3

        }

    private var actualSongName: String = ""
    private var actualSongUri: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAudioClipsBinding.inflate(inflater,container,false)

        binding.toolbarAudioClips.setNavigationOnClickListener { findNavController().popBackStack() }
        setUiBasicInfo()
        initViewModel()
        binding.barMusicPlayer.btnImvAction.setOnClickListener(this::playClicked)
        binding.barMusicPlayer.btnImvActionNext.setOnClickListener(this::nextClicked)
        binding.barMusicPlayer.btnImvActionPrevious.setOnClickListener(this::prevClicked)

        return binding.root
    }

    private fun initViewModel() {

        viewModel.onCreate(Constants.API_VERSION_ONE)

        viewModel.audioClips.observe(viewLifecycleOwner) { AudioClipsApi ->
            if (AudioClipsApi != null) {
                binding.noDataLayout.root.visibility = View.GONE
                binding.relativeNoConnection.visibility = View.GONE

                audioClipsList = ArrayList()
                audioClipsList.clear()
                audioClipsList.addAll(AudioClipsApi.audio_clips)
                initRecyclerView(binding.rvAudioClips)
                initMediaPlayer()
            } else {
                //If theres no connection de no data layout pops and make the Intent to go to wifi settings
                binding.noDataLayout.root.visibility = View.VISIBLE
                binding.relativeNoConnection.visibility = View.VISIBLE
                binding.noDataLayout.btnReconnection.setOnClickListener {
                    startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                    viewModel.onCreate(Constants.API_VERSION_TWO)
                }
            }

        }

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it

            binding.audioImv.isVisible = !it

        }

    }

    private fun initMediaPlayer() {
        actualSongName = audios[actualSongIndex].songName
        actualSongUri = audios[actualSongIndex].mp3
        binding.barMusicPlayer.podcastTitle.text = actualSongName
    }

    private fun setUiBasicInfo() {

        binding.toolbarTitle.text = requireContext().getText(R.string.audio_clips_txt)
        //To observe the state of collapsing toolbar
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                //Collapsed
                binding.toolbarTitle.visibility = View.VISIBLE
            } else {
                binding.toolbarTitle.visibility = View.GONE
            }
        })

    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.hasFixedSize()
        adapter = AudioClipsAdapter(
            audioClipsList,
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
            mediaPlayer.start()
        } else {
            binding.barMusicPlayer.btnImvAction.setImageDrawable(requireContext().getDrawable(R.drawable.ic_play))
            mediaPlayer.pause()
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

        mediaPlayer.stop()
        mediaPlayer.reset()
        val uri = actualSongUri
        mediaPlayer.setDataSource(uri)

        mediaPlayer.setOnPreparedListener { mp ->
            mp.setWakeMode(
                requireActivity().applicationContext,
                PowerManager.PARTIAL_WAKE_LOCK
            )
            mp.start()
            mp.seekTo(0)
        }


        mediaPlayer.setOnCompletionListener { refreshSong() }

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