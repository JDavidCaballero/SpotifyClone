package com.jdcm.spotifyclone.utils

class Constants {

    companion object {
        //Base url
        const val BASE_URL = "https://api.audioboom.com/"

        //Token
        const val API_VERSION_ONE = "application/json; version=1"
        const val API_VERSION_TWO = "application/json; version=2"

        //GET METHODS

        const val GET_RECOMMENDED_CHANNELS = "channels/recommended"
        const val GET_CHANNEL_AUDIO_CLIPS = "channels/{channel_id}/audio_clips"
    }

}