package com.jdcm.spotifyclone.ui.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jdcm.spotifyclone.databinding.FragmentHomeBinding
import com.jdcm.spotifyclone.ui.home.adapter.ChannelsAdapter
import com.jdcm.spotifyclone.ui.home.data.model.ChannelsModel
import com.jdcm.spotifyclone.utils.Constants.Companion.API_VERSION_TWO
import com.jdcm.spotifyclone.utils.rvListener.RecyclerItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val recommendedChannelViewModel: HomeViewModel by viewModels()
    private var recommendedList: ArrayList<ChannelsModel?>? = null

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
        recommendedChannelViewModel.onCreate(API_VERSION_TWO)

        recommendedChannelViewModel.channelModel.observe(viewLifecycleOwner) { channelApi ->

            if (!channelApi.isNullOrEmpty()) {
                //Fill the list that goes to the rv adapter and clears it for cache leaks etc
                binding.noDataLayout.root.visibility = View.GONE
                binding.relativeNoConnection.visibility = View.GONE
                recommendedList = ArrayList()
                recommendedList!!.clear()
                recommendedList!!.addAll(channelApi)
                initRecyclerView(binding.rvRecommendedPodcast)
            } else {
                //If theres no connection de no data layout pops and make the Intent to go to wifi settings
                binding.noDataLayout.root.visibility = View.VISIBLE
                binding.relativeNoConnection.visibility = View.VISIBLE
                binding.noDataLayout.btnReconnection.setOnClickListener {
                    startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                    recommendedChannelViewModel.onCreate(API_VERSION_TWO)
                }
            }

            recommendedChannelViewModel.loading.observe(viewLifecycleOwner) {
                binding.progressBar.isVisible = it
                binding.rvRecommendedPodcast.isVisible = !it
            }
        }
    }

    private fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.hasFixedSize()
        val adapter = ChannelsAdapter(recommendedList!!, requireActivity())
        recyclerView.adapter = adapter
        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(
            requireContext(),
            recyclerView,
            object : RecyclerItemClickListener.OnItemClickListener {
                override fun onItemClick(view: View?, position: Int) {
                    val action: NavDirections =
                        HomeFragmentDirections.actionNavigationHomeToPodCastDetailFragment(
                            recommendedList!![position]!!.urls.logo_image.original!!,
                            recommendedList!![position]!!.title,
                            recommendedList!![position]!!.description,
                            recommendedList!![position]!!.id

                        )
                    findNavController().navigate(action)
                }

                override fun onLongItemClick(view: View?, position: Int) {}
            }

        ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}