package com.jdcm.spotifyclone.ui.home.data.network

import com.jdcm.spotifyclone.ui.home.data.model.ChannelsResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class ChannelsService @Inject constructor(private val channelsApi: ChannelsApiClient) {

    suspend fun getRecommendedChannels(versionHeader: String): ChannelsResponseModel? {
        return withContext(Dispatchers.IO) {
            //Try catch for an exception with the Api this will return null else normal response of the Api
            try {
                val response = channelsApi.getRecommendedChannels(versionHeader)
                response.body()
            } catch (e: Exception) {
                return@withContext null
            }
        }
    }
}