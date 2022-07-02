package com.jdcm.spotifyclone.ui.home.data

import com.jdcm.spotifyclone.ui.home.data.model.ChannelsModel
import com.jdcm.spotifyclone.ui.home.data.network.ChannelsService
import javax.inject.Inject

class RecommendedChannelsRepository
@Inject
constructor(
    private val channelsApi: ChannelsService?
) {

    suspend fun getRecommendedChannels(versionHeader: String): ArrayList<ChannelsModel?> {
        return channelsApi!!.getRecommendedChannels(versionHeader)!!.body
    }

}