package com.jdcm.spotifyclone.ui.home.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jdcm.spotifyclone.R
import com.jdcm.spotifyclone.ui.home.data.model.ChannelsModel

class ChannelsAdapter(
    private var list: ArrayList<ChannelsModel?>,
    private val context: Activity,
) : RecyclerView.Adapter<ChannelsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ChannelsViewHolder(
            layoutInflater.inflate(
                R.layout.list_item_recommended_channels,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holderReception: ChannelsViewHolder, position: Int) {
        val item: ChannelsModel? = list[position]
        holderReception.bind(item,context)
    }

}