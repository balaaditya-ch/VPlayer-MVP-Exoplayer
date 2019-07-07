package com.aditya.vplayer.ui.videos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.vplayer.R
import com.aditya.vplayer.models.VideoDetails

class VideosAdapter(onClickItem: (VideoDetails) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    val click: (VideoDetails) -> Unit = {
        onClickItem.invoke(it)
    }
    val videos: ArrayList<VideoDetails> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
        return VideosViewHolder(view, click)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VideosViewHolder).bind(videos[position])
    }

    fun add(videos: ArrayList<VideoDetails>) {
        this.videos.clear()
        this.videos.addAll(videos)
        notifyDataSetChanged()
    }
}