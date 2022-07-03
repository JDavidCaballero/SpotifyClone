package com.jdcm.spotifyclone.ui.home.ui.channelDetail.data

import com.jdcm.spotifyclone.ui.home.ui.channelDetail.data.model.ChannelDetailModel
import com.jdcm.spotifyclone.ui.home.ui.channelDetail.data.network.ChannelDetailService
import javax.inject.Inject

class ChannelDetailRepository
@Inject
constructor(
    private val channelDetailApi: ChannelDetailService?
) {

    suspend fun getChannelDetail(versionHeader: String, podCastId : Int): ChannelDetailModel? {
        return if (channelDetailApi!!.getChannelDetail(versionHeader,podCastId) != null) {
            channelDetailApi.getChannelDetail(versionHeader,podCastId)!!.body
        } else {
            null
        }
    }

}