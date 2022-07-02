package com.jdcm.spotifyclone.ui.home.data.model

import com.google.gson.annotations.SerializedName

data class ChannelsModel(
    @SerializedName("type") val type: String,
    @SerializedName("id") val id: Int,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("formatted_description") val formatted_description: String,
    @SerializedName("parent_channel_id") val parent_channel_id: String,
    @SerializedName("channel_style") val channel_style: String,
    @SerializedName("urls") val urls: Urls,
    @SerializedName("recommendation") val recommendation: Recommendation
)

data class Urls(
    @SerializedName("web_url") val web_url: String,
    @SerializedName("logo_image") val logo_image: LogoImage,
    @SerializedName("banner_image") val banner_image: BannerImage
)

data class LogoImage(
    @SerializedName("original") val original: String
)

data class BannerImage(
    @SerializedName("original") val original: String
)


data class Recommendation(
    @SerializedName("position") val position: Int,
    @SerializedName("category_id") val category_id: Int,
    @SerializedName("default_follow") val default_follow: Boolean
)
