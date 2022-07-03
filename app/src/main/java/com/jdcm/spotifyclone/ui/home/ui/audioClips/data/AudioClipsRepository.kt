package com.jdcm.spotifyclone.ui.home.ui.audioClips.data

import com.jdcm.spotifyclone.ui.home.ui.audioClips.data.model.AudioClipsModel
import com.jdcm.spotifyclone.ui.home.ui.audioClips.data.network.AudioClipsService
import javax.inject.Inject

class AudioClipsRepository
@Inject
constructor(
    private val audioClipsApi: AudioClipsService?
) {

    suspend fun getAudioClips(versionHeader: String): AudioClipsModel? {
        return if (audioClipsApi!!.getAudioClips(versionHeader) != null) {
            audioClipsApi.getAudioClips(versionHeader)!!.body
        } else {
            null
        }
    }

}