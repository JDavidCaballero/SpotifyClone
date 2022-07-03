package com.jdcm.spotifyclone.ui.home.ui.audioClips.data.network

import com.jdcm.spotifyclone.ui.home.ui.audioClips.data.model.AudioClipsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class AudioClipsService @Inject constructor(private val audioClipsApi: AudioClipsApiClient) {

    suspend fun getAudioClips(versionHeader: String): AudioClipsResponse? {
        return withContext(Dispatchers.IO) {
            //Try catch for an exception with the Api this will return null else normal response of the Api
            try {
                val response = audioClipsApi.getAudioClips(versionHeader)
                response.body()
            } catch (e: Exception) {
                return@withContext null
            }
        }
    }
}