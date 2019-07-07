package com.aditya.vplayer.di.videos

import com.aditya.vplayer.models.VideoDetails
import com.aditya.vplayer.repo.local.LocalDatabaseSchema
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VideosApi {

    ///media.json?print=pretty
    @GET("/media.json")
    fun getVideos(
        @Query("print") print: String
    ): Single<Response<ArrayList<VideoDetails>>>
}