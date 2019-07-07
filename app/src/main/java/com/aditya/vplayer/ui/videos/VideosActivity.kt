package com.aditya.vplayer.ui.videos

import android.os.Bundle
import com.aditya.vplayer.R
import com.aditya.vplayer.base.BaseActivity
import com.aditya.vplayer.core.openActivity
import com.aditya.vplayer.models.VideoDetails
import com.aditya.vplayer.ui.player.PlayerActivity

class VideosActivity : BaseActivity(), OpenHelper {

    override fun OnPlayVideo(videoDetails: VideoDetails) {
        val bundle = Bundle()
        bundle.putParcelable(getString(R.string.selectedVideo), videoDetails)
        openActivity(PlayerActivity::class.java, bundle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replace(fragment = VideosFragment())
    }
}

