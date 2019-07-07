package com.aditya.vplayer.ui.player

import android.os.Bundle
import com.aditya.vplayer.R
import com.aditya.vplayer.base.BaseActivity
import com.aditya.vplayer.models.VideoDetails

class PlayerActivity : BaseActivity() {
    companion object {
        const val SELECTED: Int = 0
        const val UNSELECTED: Int = 1
    }

    lateinit var videoDetails: VideoDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        videoDetails = intent.getParcelableExtra(getString(R.string.selectedVideo))
        val fragment = PlayerFragment()
        replace(fragment)
    }
}

