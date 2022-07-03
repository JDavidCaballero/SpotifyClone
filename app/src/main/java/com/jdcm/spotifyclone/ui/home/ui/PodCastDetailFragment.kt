package com.jdcm.spotifyclone.ui.home.ui

import android.content.Intent
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
import com.jdcm.spotifyclone.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class PodCastDetailFragment : Fragment() {

    private lateinit var binding: FragmentPodCastDetailBinding
    private val viewModel: PodCastDetailViewModel by viewModels()
    private val channelId by lazy { PodCastDetailFragmentArgs.fromBundle(requireArguments()).channelId }
    private var audioClipsList: ArrayList<AudioClips>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPodCastDetailBinding.inflate(inflater, container, false)
        binding.toolbarPodCastDetail.setNavigationOnClickListener { findNavController().popBackStack() }



        setUiBasicInfo()
        initViewModel()

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

    private fun setUiBasicInfo() {
        //Podcast Logo
        Glide.with(requireContext())
            .load(PodCastDetailFragmentArgs.fromBundle(requireArguments()).channelLogo)
            .placeholder(R.drawable.ic_podcast)
            .into(binding.podcastImv)
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
        val adapter = ChannelDetailAdapter(audioClipsList!!, requireActivity())
        recyclerView.adapter = adapter

    }


}