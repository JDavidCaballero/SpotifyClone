package com.jdcm.spotifyclone.ui.home.ui.audioClips.data.network

import com.jdcm.spotifyclone.ui.home.data.model.ChannelsResponseModel
import com.jdcm.spotifyclone.ui.home.ui.audioClips.data.model.AudioClipsResponse
import com.jdcm.spotifyclone.utils.Constants.Companion.GET_AUDIO_CLIPS
import com.jdcm.spotifyclone.utils.Constants.Companion.GET_RECOMMENDED_CHANNELS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface AudioClipsApiClient {

    @GET(GET_AUDIO_CLIPS)
    suspend fun getAudioClips(
        @Header("Accept") header :String
    ): Response<AudioClipsResponse>

}