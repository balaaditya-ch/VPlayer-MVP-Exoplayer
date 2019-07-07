package com.aditya.vplayer.ui.player

import com.aditya.vplayer.repo.VPlayerRepository
import com.aditya.vplayer.repo.local.LocalDatabaseSchema


class PlayerPresenter(private val vPlayerRepository: VPlayerRepository) : PlayerContract.Presenter {

    private var view: PlayerContract.View? = null

    override fun getSuggestionList(id: Long): List<LocalDatabaseSchema.VideoMetaEntity> {
        return vPlayerRepository.getLocalVideos(id)
    }

    override fun updatePlayback(id: Long, playback: Long) {
        vPlayerRepository.updateVideoPlayback(id, playback)
    }

    override fun getPlayback(id: Long): Long {
        return vPlayerRepository.getPlayBackById(id)
    }

    override fun attachview(view: PlayerContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }
}