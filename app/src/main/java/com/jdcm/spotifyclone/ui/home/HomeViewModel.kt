package com.jdcm.spotifyclone.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdcm.spotifyclone.ui.home.data.RecommendedChannelsRepository
import com.jdcm.spotifyclone.ui.home.data.model.ChannelsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject
constructor(private val recommendedChannelRepository: RecommendedChannelsRepository) : ViewModel() {

    val channelModel = MutableLiveData<ArrayList<ChannelsModel?>>()
    val loading = MutableLiveData<Boolean>()

    fun onCreate(versionHeader: String) {
        viewModelScope.launch {
            loading.postValue(true)
            val result = recommendedChannelRepository.getRecommendedChannels(versionHeader)
            //Values to Fragment
            if (result != null) {
                channelModel.postValue(result)
            } else {
                channelModel.postValue(null)
            }
            loading.postValue(false)
        }


    }

}