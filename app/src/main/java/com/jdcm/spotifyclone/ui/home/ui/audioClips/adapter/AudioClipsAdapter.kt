package com.jdcm.spotifyclone.ui.home.ui.audioClips.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jdcm.spotifyclone.R
import com.jdcm.spotifyclone.ui.home.ui.audioClips.data.model.AudioClips
import com.jdcm.spotifyclone.ui.home.ui.channelDetail.data.model.ChannelAudioClips
import com.jdcm.spotifyclone.utils.rvListener.ItemSongsClickListener

class AudioClipsAdapter(
    private var list: ArrayList<AudioClips>?,
    private val context: Activity,
    private val clickListener: ItemSongsClickListener
) : RecyclerView.Adapter<AudioClipsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioClipsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AudioClipsViewHolder(
            layoutInflater.inflate(
                R.layout.list_item_podcast,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list!!.size

    override fun onBindViewHolder(holderReception: AudioClipsViewHolder, position: Int) {
        val item: AudioClips = list!![position]
        holderReception.bind(item, context, clickListener,position)
    }

}