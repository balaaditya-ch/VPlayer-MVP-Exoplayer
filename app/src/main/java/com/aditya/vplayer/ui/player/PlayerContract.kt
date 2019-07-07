package com.aditya.vplayer.ui.player

import com.aditya.vplayer.base.BasePresenter
import com.aditya.vplayer.base.BaseView
import com.aditya.vplayer.models.VideoDetails
import com.aditya.vplayer.repo.local.LocalDatabaseSchema


interface PlayerContract {

    interface View : BaseView {

        fun playVideo(playbackPosition: Long)

        fun setSuggestionAdapter(suggestionList: ArrayList<VideoDetails>)

        fun setPlayback(id: Long, playback: Long)

        fun getSuggestionList()

        fun viewBinds()
    }

    interface Presenter : BasePresenter<View> {

        fun updatePlayback(id: Long, playback: Long)

        fun getPlayback(id: Long): Long

        fun getSuggestionList(id: Long): List<LocalDatabaseSchema.VideoMetaEntity>

    }
}