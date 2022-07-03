package com.jdcm.spotifyclone.ui.home.ui.channelDetail.data.network

import com.jdcm.spotifyclone.ui.home.ui.channelDetail.data.model.ChannelDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class ChannelDetailService @Inject constructor(private val channelsApi: ChannelDetailApiClient) {

    suspend fun getChannelDetail(
        versionHeader: String,
        podcastId: Int
    ): ChannelDetailResponse? {
        return withContext(Dispatchers.IO) {
            //Try catch for an exception with the Api this will return null else normal response of the Api
            try {
                val response = channelsApi.getChannelDetail(versionHeader, podcastId)
                response.body()
            } catch (e: Exception) {
                return@withContext null
            }
        }
    }
}