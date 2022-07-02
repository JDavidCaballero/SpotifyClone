package com.jdcm.spotifyclone.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jdcm.spotifyclone.databinding.FragmentHomeBinding
import com.jdcm.spotifyclone.utils.Constants.Companion.API_VERSION
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val recommendedChannelViewModel : HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        initViewModel()

        return binding.root
    }

    private fun initViewModel() {
        recommendedChannelViewModel.onCreate(API_VERSION)

        recommendedChannelViewModel.channelModel.observe(viewLifecycleOwner){ channelApi->

            if (!channelApi.isNullOrEmpty()){



            }else{



            }



        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}