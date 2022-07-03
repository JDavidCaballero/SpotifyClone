package com.jdcm.spotifyclone.ui.home.ui.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jdcm.spotifyclone.R
import com.jdcm.spotifyclone.databinding.ListItemPodcastBinding
import com.jdcm.spotifyclone.ui.home.ui.data.model.AudioClips
import com.jdcm.spotifyclone.utils.rvListener.ItemSongsClickListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ChannelDetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ListItemPodcastBinding.bind(view)
    private val timeFormat = SimpleDateFormat("d MMM yy")
    private var inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var selected = -1

    @SuppressLint("SetTextI18n")
    fun bind(
        channelAudioClips: AudioClips,
        context: Activity,
        clickListener: ItemSongsClickListener,
        position: Int
    ) {
        //Image
        Glide.with(context)
            .load(channelAudioClips.channel.urls.logo_image.original)
            .placeholder(R.drawable.ic_podcast)
            .into(binding.imvPodcast)
        //Title
        binding.podcastTitle.text = channelAudioClips.title
        //SubTitle
        binding.podcastDescription.text = channelAudioClips.description
        //Date
        val date = inputFormat.parse(channelAudioClips.updated_at.substring(0, 10))
        binding.podcastDate.text = timeFormat.format(date!!)
        //Duration
        val time = channelAudioClips.duration / 60
        binding.podcastDuration.text = "${time.toString().substring(0, 3)} Min"

        if (position == selected){
            Glide.with(context)
                .load(R.drawable.ic_pause_circle)
                .placeholder(R.drawable.ic_podcast)
                .into(binding.imActionButton)

        }else{


            Glide.with(context)
                .load(R.drawable.ic_play_circle)
                .placeholder(R.drawable.ic_podcast)
                .into(binding.imActionButton)
        }

        binding.imActionButton.setOnClickListener {
            selected = position
            clickListener.onClickPlay(position = position)
        }



    }
}