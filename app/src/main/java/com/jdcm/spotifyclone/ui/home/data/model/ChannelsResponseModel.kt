package com.jdcm.spotifyclone.ui.home.data.model

import com.google.gson.annotations.SerializedName

data class ChannelsResponseModel(

    @SerializedName("body") val body: ArrayList<ChannelsModel?>

)