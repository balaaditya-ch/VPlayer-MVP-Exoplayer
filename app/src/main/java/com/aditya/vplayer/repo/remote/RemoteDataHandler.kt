package com.aditya.vplayer.repo.remote

import android.content.Context
import android.net.ConnectivityManager
import com.aditya.vplayer.di.videos.VideosApi
import com.aditya.vplayer.models.VideoDetails
import com.aditya.vplayer.repo.Repository
import com.aditya.vplayer.repo.local.LocalDatabaseSchema
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class RemoteDataHandler(private val videosApi: VideosApi) {

    @Inject
    lateinit var context: Context

    private fun checkNetwork(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        val networkInfo = connectivityManager!!.activeNetworkInfo

        return (networkInfo != null && networkInfo.isConnectedOrConnecting)

    }


    fun getVideos(callback: Repository.GetVideos) {


//        val videosResponse = VPlayerResponse<ArrayList<VideoDetails>>()
//        videosResponse.networkState
        val flowable = videosApi.getVideos("pretty")

        CompositeDisposable().add(flowable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Response<ArrayList<VideoDetails>>>() {
                override fun onSuccess(t: Response<ArrayList<VideoDetails>>) {
                    t.body()?.let {
                        callback.onDataAvailable(it)
                    }
                }

                override fun onError(e: Throwable) {
                    val exception = VPlayerBaseException(e.message ?: "", 202, e.cause)
                    callback.onDataNotAvailable(exception)
                }

            })
        )

    }
}