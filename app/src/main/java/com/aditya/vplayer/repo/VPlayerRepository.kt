package com.aditya.vplayer.repo

import com.aditya.vplayer.di.videos.VideosApi
import com.aditya.vplayer.repo.local.LocalDatabaseSchema
import com.aditya.vplayer.repo.local.VPlayerDatabase
import com.aditya.vplayer.repo.remote.RemoteDataHandler


class VPlayerRepository(
    videosApi: VideosApi,
    private val vPlayerDatabase: VPlayerDatabase
) : Repository {

    override fun getPlayBackById(id: Long): Long {
        return vPlayerDatabase.videoDetailsDao().getPlayBackById(id)
    }

    override fun getTotalVideosCount(): Long {
        return vPlayerDatabase.videoDetailsDao().getRowsCount()
    }

    override fun insertVideos(list: ArrayList<LocalDatabaseSchema.VideoMetaEntity>) {
        vPlayerDatabase.videoDetailsDao().insertAll(list)
    }

    override fun getLocalVideos(id: Long): List<LocalDatabaseSchema.VideoMetaEntity> {
        return vPlayerDatabase.videoDetailsDao().getLocalVideos(id)
    }

    override fun getLocalVideos(): List<LocalDatabaseSchema.VideoMetaEntity> {
        return vPlayerDatabase.videoDetailsDao().getAll()
    }

    override fun updateVideoPlayback(id: Long, playback: Long) {
        vPlayerDatabase.videoDetailsDao().updateAlertStatus(id, playback)
    }

    private val remoteDataHandler = RemoteDataHandler(videosApi)

    override fun getVideos(callbacks: Repository.GetVideos) {
        remoteDataHandler.getVideos(callbacks)
    }
}