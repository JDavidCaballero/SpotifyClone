package com.jdcm.spotifyclone.ui.home.ui.audioClips.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jdcm.spotifyclone.R
import com.jdcm.spotifyclone.databinding.ListItemPodcastBinding
import com.jdcm.spotifyclone.ui.home.ui.audioClips.data.model.AudioClips
import com.jdcm.spotifyclone.utils.rvListener.ItemSongsClickListener
import java.text.DateFormat
import java.text.SimpleDateFormat

class AudioClipsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ListItemPodcastBinding.bind(view)
    private val timeFormat = SimpleDateFormat("d MMM yy")
    private var inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var selected = -1

    @SuppressLint("SetTextI18n")
    fun bind(
        audioClips: AudioClips,
        context: Activity,
        clickListener: ItemSongsClickListener,
        position: Int
    ) {
        //Image
        Glide.with(context)
            .load(audioClips.channel.urls.logo_image.original)
            .placeholder(R.drawable.ic_podcast)
            .into(binding.imvPodcast)
        //Title
        binding.podcastTitle.text = audioClips.title
        //SubTitle
        binding.podcastDescription.text = if (!audioClips.description.isNullOrEmpty()) {
            audioClips.description
        } else {
            context.getString(R.string.no_dedscription_txt)
        }


        //Date
        val date = inputFormat.parse(audioClips.updated_at.substring(0, 10))
        binding.podcastDate.text = timeFormat.format(date!!)
        //Duration
        val time = audioClips.duration / 60
        binding.podcastDuration.text = "${time.toString().substring(0, 3)} Min"

        binding.cardAudioClip.setOnClickListener {

            selected = position

            clickListener.onClickPlay(position = position)

        }

        if (position != selected) {
            binding.imActionButton.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.imActionButton.context,
                    R.drawable.ic_play_circle
                )
            )
        } else {
            binding.imActionButton.setImageDrawable(
                ContextCompat.getDrawable(
                    binding.imActionButton.context,
                    R.drawable.ic_pause_circle
                )
            )
        }


    }
}