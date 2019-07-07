package com.aditya.vplayer.ui.player

import android.graphics.drawable.Animatable2
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.aditya.vplayer.R
import com.aditya.vplayer.models.VideoDetails
import com.aditya.vplayer.ui.player.PlayerActivity.Companion.SELECTED
import com.bumptech.glide.Glide

class SuggestionsViewHolder(private val view: View, private val click: (VideoDetails, Int) -> Unit) :
    RecyclerView.ViewHolder(view), View.OnClickListener {

    override fun onClick(v: View?) {
        click.invoke(videoDetails, pos)
    }

    private val tittle: TextView = view.findViewById(R.id.title)
    private val description: TextView = view.findViewById(R.id.description)
    private val thumb: ImageView = view.findViewById(R.id.thumb)
    private val selectionImage: ImageView = view.findViewById(R.id.selectionImage)
    private lateinit var videoDetails: VideoDetails
    private var pos = 0


    fun bind(videoDetails: VideoDetails, position: Int) {
        view.setOnClickListener(this)
        this.pos = position
        this.videoDetails = videoDetails
        tittle.text = videoDetails.title
        description.text = videoDetails.description

        if (videoDetails.selected == SELECTED) {
            selectionImage.visibility = View.VISIBLE
            animateImage(selectionImage)
        } else {
            selectionImage.visibility = View.INVISIBLE
        }

//        Picasso.get().load(videoDetails.thumb).placeholder(R.drawable.ic_placeholder).into(thumb)
        Glide.with(view.context)
            .load(videoDetails.thumb)
            .placeholder(R.drawable.ic_placeholder)
            .into(thumb)
    }

    private fun animateImage(selectionImage: ImageView) {

        val d = selectionImage.drawable
        if (d is AnimatedVectorDrawableCompat) {
            d.start()
            d.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable?) {
                    super.onAnimationEnd(drawable)
                    d.start()
                }
            })

        } else if (d is AnimatedVectorDrawable) {
            d.start()
            d.registerAnimationCallback(object : Animatable2.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable?) {
                    super.onAnimationEnd(drawable)
                    d.start()
                }
            })
        }

    }
}