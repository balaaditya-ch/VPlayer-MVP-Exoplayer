package com.aditya.vplayer.ui.videos

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aditya.vplayer.R
import com.aditya.vplayer.models.VideoDetails
import com.squareup.picasso.Picasso

class VideosViewHolder(private val view: View, private val click: (VideoDetails) -> Unit) :
    RecyclerView.ViewHolder(view), View.OnClickListener {


    override fun onClick(v: View?) {
        click.invoke(videoDetails)
    }


    private val tittle: TextView = view.findViewById(R.id.tittle)
    private val description: TextView = view.findViewById(R.id.description)
    private val thumb: ImageView = view.findViewById(R.id.thumpImg)
    private lateinit var videoDetails: VideoDetails

    fun bind(videoDetails: VideoDetails) {
        view.setOnClickListener(this)
        this.videoDetails = videoDetails
        tittle.text = videoDetails.title
        description.text = videoDetails.description
        Picasso.get().load(videoDetails.thumb).placeholder(R.drawable.ic_placeholder).into(thumb)
//        Glide.with(view.context)
//            .load(videoDetails.thumb)
//            .placeholder(R.drawable.ic_placeholder)
//            .into(thumb)
    }
}