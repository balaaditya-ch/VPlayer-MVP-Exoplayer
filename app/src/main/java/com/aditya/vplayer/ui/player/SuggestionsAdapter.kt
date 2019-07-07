package com.aditya.vplayer.ui.player

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.vplayer.R
import com.aditya.vplayer.models.VideoDetails
import com.aditya.vplayer.ui.player.PlayerActivity.Companion.SELECTED
import com.aditya.vplayer.ui.player.PlayerActivity.Companion.UNSELECTED

class SuggestionsAdapter(onClickItem: (VideoDetails, Int) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var currentPlaying: Int = 0

    private val click: (VideoDetails, Int) -> Unit = { i, j ->
        onClickItem.invoke(i, j)
    }

    val videos: ArrayList<VideoDetails> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_item2, parent, false)
        return SuggestionsViewHolder(view, click)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val videoDetails = videos[position]
        (holder as SuggestionsViewHolder).bind(videoDetails, position)
    }

    fun add(videos: ArrayList<VideoDetails>) {
        this.videos.clear()
        this.videos.addAll(videos)
        notifyDataSetChanged()
    }

    fun playNext(): VideoDetails {
        this.videos[currentPlaying].selected = UNSELECTED
        if (currentPlaying < videos.size - 1) {
            currentPlaying++
            videos[currentPlaying].selected = SELECTED
        }
        notifyDataSetChanged()
        return videos[currentPlaying]
    }

    fun playSelected(pos: Int): VideoDetails {
        videos[currentPlaying].selected = UNSELECTED
        currentPlaying = pos
        videos[currentPlaying].selected = SELECTED
        notifyDataSetChanged()
        return videos[currentPlaying]
    }
}