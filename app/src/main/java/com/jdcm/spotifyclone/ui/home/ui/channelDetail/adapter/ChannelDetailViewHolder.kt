package com.jdcm.spotifyclone.ui.home.ui.channelDetail.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jdcm.spotifyclone.R
import com.jdcm.spotifyclone.databinding.ListItemPodcastBinding
import com.jdcm.spotifyclone.ui.home.ui.channelDetail.data.model.ChannelAudioClips
import com.jdcm.spotifyclone.utils.rvListener.ItemSongsClickListener
import java.text.DateFormat
import java.text.SimpleDateFormat

class ChannelDetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ListItemPodcastBinding.bind(view)
    private val timeFormat = SimpleDateFormat("d MMM yy")
    private var inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var selected = -1

    @SuppressLint("SetTextI18n")
    fun bind(
        channelAudioClips: ChannelAudioClips,
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
        binding.podcastDescription.text = if (!channelAudioClips.description.isNullOrEmpty()) {
            channelAudioClips.description
        } else {
            context.getString(R.string.no_dedscription_txt)
        }

        //Date
        val date = inputFormat.parse(channelAudioClips.updated_at.substring(0, 10))
        binding.podcastDate.text = timeFormat.format(date!!)
        //Duration
        val time = channelAudioClips.duration / 60
        binding.podcastDuration.text = "${time.toString().substring(0, 3)} Min"

        binding.imActionButton.setOnClickListener {
            binding.imActionButton.setImageDrawable(ContextCompat.getDrawable(binding.imActionButton.context,R.drawable.ic_pause_circle))

            selected = position



            clickListener.onClickPlay(position = position)



        }

        if (position != selected){
            binding.imActionButton.setImageDrawable(ContextCompat.getDrawable(binding.imActionButton.context,R.drawable.ic_play_circle))
        }





    }
}