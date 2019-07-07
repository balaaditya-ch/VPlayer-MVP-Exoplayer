package com.aditya.vplayer.repo

import com.aditya.vplayer.models.VideoDetails
import com.aditya.vplayer.repo.local.LocalDatabaseSchema
import com.aditya.vplayer.repo.remote.VPlayerBaseException

interface Repository {

    fun getVideos(callbacks: GetVideos)

    fun getLocalVideos(id: Long): List<LocalDatabaseSchema.VideoMetaEntity>

    fun getLocalVideos(): List<LocalDatabaseSchema.VideoMetaEntity>

    fun updateVideoPlayback(id: Long, playback: Long)

    fun insertVideos(list: ArrayList<LocalDatabaseSchema.VideoMetaEntity>)

    fun getTotalVideosCount(): Long

    fun getPlayBackById(id: Long): Long

    interface GetVideos : BaseFlow {

        fun onDataAvailable(videos: ArrayList<VideoDetails>)
    }

    interface BaseFlow {
        fun onDataNotAvailable(exception: VPlayerBaseException)
    }
}