package com.aditya.vplayer.ui.videos

import com.aditya.vplayer.base.BasePresenter
import com.aditya.vplayer.base.BaseView
import com.aditya.vplayer.models.VideoDetails

interface VideosContract {

    interface View : BaseView {
        fun ShowVideos(videos: ArrayList<VideoDetails>)
        fun showError(message: String)
    }

    interface VideoPresenter : BasePresenter<View> {

        fun fetchVideos()

    }
}