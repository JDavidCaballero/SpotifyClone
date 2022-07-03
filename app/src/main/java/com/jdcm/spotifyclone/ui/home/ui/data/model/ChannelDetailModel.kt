package com.jdcm.spotifyclone.ui.home.ui.data.model

import com.google.gson.annotations.SerializedName
import com.jdcm.spotifyclone.ui.home.data.model.LogoImage

data class ChannelDetailModel(
    @SerializedName("totals") val totals: Totals,
    @SerializedName("audio_clips") val audio_clips: ArrayList<AudioClips>
)

data class Totals(
    @SerializedName("count") val count: Int,
    @SerializedName("offset") val offset: Int
)


data class AudioClips(
    @SerializedName("id") val id: Int,
    @SerializedName("season_number") val season_number: String,
    @SerializedName("episode_number") val episode_number: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("formatted_description") val formatted_description: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("user") val user: User?,
    @SerializedName("link_style") val link_style: String,
    @SerializedName("channel") val channel: Channel,
    @SerializedName("duration") val duration: Double,
    @SerializedName("mp3_filesize") val mp3_filesize: Int,
    @SerializedName("uploaded_at") val uploaded_at: String,
    @SerializedName("recorded_at") val recorded_at: String,
    @SerializedName("uploaded_at_ts") val uploaded_at_ts: Int,
    @SerializedName("recorded_at_ts") val recorded_at_ts: Int,
    @SerializedName("can_comment") val can_comment: Boolean,
    @SerializedName("can_embed") val can_embed: Boolean,
    @SerializedName("category_id") val category_id: Int,
    @SerializedName("counts") val counts: Counts,
    @SerializedName("urls") val urls: Urls,
    @SerializedName("image_attachment") val image_attachment: Int
)

data class Channel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("urls") val urls: Urls
)

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("urls") val urls: Urls
)

data class Urls(
    @SerializedName("detail") val detail: String,
    @SerializedName("high_mp3") val high_mp3: String,
    @SerializedName("image") val image: String,
    @SerializedName("post_image") val post_image: PostImage,
    @SerializedName("wave_img") val wave_img: String,
    @SerializedName("logo_image") val logo_image: LogoImage

)

data class PostImage(
    @SerializedName("original") val original: String
)

data class Counts(
    @SerializedName("comments") val comments: Int,
    @SerializedName("likes") val likes: Int,
    @SerializedName("plays") val plays: Int
)