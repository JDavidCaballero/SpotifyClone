package com.jdcm.spotifyclone.ui.home.ui.channelDetail.data.network

import com.jdcm.spotifyclone.ui.home.ui.channelDetail.data.model.ChannelDetailResponse
import com.jdcm.spotifyclone.utils.Constants.Companion.GET_CHANNEL_AUDIO_CLIPS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ChannelDetailApiClient {

    @GET(GET_CHANNEL_AUDIO_CLIPS)
    suspend fun getChannelDetail(
        @Header("Accept") header :String,
        @Path("channel_id") channelId : Int
    ): Response<ChannelDetailResponse>

}