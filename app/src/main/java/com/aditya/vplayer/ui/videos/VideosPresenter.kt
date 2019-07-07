package com.aditya.vplayer.ui.videos

import android.widget.Toast
import com.aditya.vplayer.models.VideoDetails
import com.aditya.vplayer.repo.Repository
import com.aditya.vplayer.repo.VPlayerRepository
import com.aditya.vplayer.repo.local.LocalDatabaseSchema
import com.aditya.vplayer.repo.remote.VPlayerBaseException
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.collections.ArrayList


class VideosPresenter(private val vPlayerRepository: VPlayerRepository) : VideosContract.VideoPresenter {

    private val compositeDisposable = CompositeDisposable()
    private var view: VideosContract.View? = null

    override fun fetchVideos() {

        if (vPlayerRepository.getTotalVideosCount() > 0) {
            val list = vPlayerRepository.getLocalVideos()
            val listVideos = ArrayList<VideoDetails>()
            list.forEach {
                val details = VideoDetails(it.description, it.id.toString(), it.thumb, it.title, it.url)
                listVideos.add(details)
            }
            view?.ShowVideos(listVideos)
        } else {
            vPlayerRepository.getVideos(object : Repository.GetVideos {

                override fun onDataAvailable(videos: ArrayList<VideoDetails>) {
                    view?.ShowVideos(videos)
                    saveToRepo(videos)
                }

                override fun onDataNotAvailable(exception: VPlayerBaseException) {
                    view?.showError(exception.details)
                }
            })
        }
    }

    private fun saveToRepo(videos: ArrayList<VideoDetails>) {
        val observable = Observable.fromIterable(videos)
        val localVideos = ArrayList<LocalDatabaseSchema.VideoMetaEntity>()
        observable.map {
            val video = Gson().fromJson<LocalDatabaseSchema.VideoMetaEntity>(
                Gson().toJson(it),
                LocalDatabaseSchema.VideoMetaEntity::class.java
            )
            video.playback = 0L
            localVideos.add(video)
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            vPlayerRepository.insertVideos(localVideos)
        }
//        val localVideos = Gson().fromJson<ArrayList<LocalDatabaseSchema.VideoMetaEntity>>(Gson().toJson(videos),LocalDatabaseSchema.VideoMetaEntity::class.java)


    }


    override fun attachview(view: VideosContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }
}