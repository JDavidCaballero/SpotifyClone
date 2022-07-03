package com.jdcm.spotifyclone.ui.home.ui.channelDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdcm.spotifyclone.ui.home.ui.channelDetail.data.ChannelDetailRepository
import com.jdcm.spotifyclone.ui.home.ui.channelDetail.data.model.ChannelDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PodCastDetailViewModel @Inject
constructor(private val repository: ChannelDetailRepository) : ViewModel() {

    val channelDetail = MutableLiveData<ChannelDetailModel>()
    val loading = MutableLiveData<Boolean>()

    fun onCreate(versionHeader: String, podCastId: Int) {
        viewModelScope.launch {
            loading.postValue(true)
            val result = repository.getChannelDetail(versionHeader, podCastId)
            channelDetail.postValue(result)
            loading.postValue(false)
        }
    }
}