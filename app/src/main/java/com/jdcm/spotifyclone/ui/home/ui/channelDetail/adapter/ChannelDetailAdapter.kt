package com.jdcm.spotifyclone.ui.home.ui.channelDetail.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jdcm.spotifyclone.R
import com.jdcm.spotifyclone.ui.home.ui.channelDetail.data.model.ChannelAudioClips
import com.jdcm.spotifyclone.utils.rvListener.ItemSongsClickListener

class ChannelDetailAdapter(
    private var list: ArrayList<ChannelAudioClips>?,
    private val context: Activity,
    private val clickListener: ItemSongsClickListener
) : RecyclerView.Adapter<ChannelDetailViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelDetailViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ChannelDetailViewHolder(
            layoutInflater.inflate(
                R.layout.list_item_podcast,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list!!.size

    override fun onBindViewHolder(holderReception: ChannelDetailViewHolder, position: Int) {
        val item: ChannelAudioClips = list!![position]
        holderReception.bind(item, context, clickListener,position)
    }

}