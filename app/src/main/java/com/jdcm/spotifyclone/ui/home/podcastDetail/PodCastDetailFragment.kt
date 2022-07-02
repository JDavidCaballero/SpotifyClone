package com.jdcm.spotifyclone.ui.home.podcastDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jdcm.spotifyclone.databinding.FragmentPodCastDetailBinding

class PodCastDetailFragment : Fragment() {

    private lateinit var binding: FragmentPodCastDetailBinding
    private  val viewModel: PodCastDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPodCastDetailBinding.inflate(inflater, container, false)


        return binding.root
    }


}