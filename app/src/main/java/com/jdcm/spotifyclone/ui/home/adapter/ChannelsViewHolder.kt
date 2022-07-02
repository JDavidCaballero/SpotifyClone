package com.jdcm.spotifyclone.ui.home.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jdcm.spotifyclone.R
import com.jdcm.spotifyclone.databinding.ListItemRecommendedChannelsBinding
import com.jdcm.spotifyclone.ui.home.data.model.ChannelsModel

class ChannelsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ListItemRecommendedChannelsBinding.bind(view)
    @SuppressLint("SetTextI18n")
    fun bind(
        channelInfo: ChannelsModel?,
        context: Activity,
    ) {
        //Image
        Glide.with(context)
            .load(channelInfo!!.urls.logo_image.original)
            .placeholder(R.drawable.ic_podcast)
            .into(binding.imPodcast)
        //Title
        binding.podcastTitle.text = channelInfo.title
        //SubTitle
        binding.podcastDescription.text = channelInfo.description
    }
}