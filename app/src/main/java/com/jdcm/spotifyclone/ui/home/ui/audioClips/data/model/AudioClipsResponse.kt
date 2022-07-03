package com.jdcm.spotifyclone.ui.home.ui.audioClips.data.model

import com.google.gson.annotations.SerializedName

data class AudioClipsResponse (

    @SerializedName("window") val window : Int,
    @SerializedName("version") val version : Int,
    @SerializedName("timestamp") val timestamp : Int,
    @SerializedName("api_warning") val api_warning : String,
    @SerializedName("body") val body : AudioClipsModel

)