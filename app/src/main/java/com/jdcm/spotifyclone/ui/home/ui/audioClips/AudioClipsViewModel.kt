package com.jdcm.spotifyclone.ui.home.ui.audioClips

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdcm.spotifyclone.ui.home.ui.audioClips.data.AudioClipsRepository
import com.jdcm.spotifyclone.ui.home.ui.audioClips.data.model.AudioClipsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AudioClipsViewModel @Inject
constructor(private val repository: AudioClipsRepository) : ViewModel() {

    val audioClips = MutableLiveData<AudioClipsModel>()
    val loading = MutableLiveData<Boolean>()

    fun onCreate(versionHeader: String) {
        viewModelScope.launch {
            loading.postValue(true)
            val result = repository.getAudioClips(versionHeader)
            audioClips.postValue(result)
            loading.postValue(false)
        }
    }
}