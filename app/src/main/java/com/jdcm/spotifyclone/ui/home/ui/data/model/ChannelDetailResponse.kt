package com.jdcm.spotifyclone.ui.home.ui.data.model

import com.google.gson.annotations.SerializedName

data class ChannelDetailResponse(

    @SerializedName("window") val window: Int,
    @SerializedName("version")  val version: Int,
    @SerializedName("timestamp") val timestamp: Int,
    @SerializedName("body") val body: ChannelDetailModel

)