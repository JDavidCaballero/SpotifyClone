package com.jdcm.spotifyclone.ui.home.data.network

import com.jdcm.spotifyclone.ui.home.data.model.ChannelsResponseModel
import com.jdcm.spotifyclone.utils.Constants.Companion.GET_RECOMMENDED_CHANNELS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ChannelsApiClient {

    @GET(GET_RECOMMENDED_CHANNELS)
    suspend fun getRecommendedChannels(
        @Header("Accept") header :String
    ): Response<ChannelsResponseModel>

}