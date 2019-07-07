package com.aditya.vplayer.di.videos

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.aditya.vplayer.core.VPlayerApplication
import com.aditya.vplayer.repo.VPlayerRepository
import com.aditya.vplayer.repo.local.VPlayerDatabase
import com.aditya.vplayer.ui.player.PlayerContract
import com.aditya.vplayer.ui.player.PlayerPresenter
import com.aditya.vplayer.ui.videos.VideosContract
import com.aditya.vplayer.ui.videos.VideosPresenter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

@Module
class VideosModule {

    @Inject
    internal lateinit var application: Application


    @VideosScope
    @Provides
    fun provideVieosApi(retrofit: Retrofit): VideosApi {
        return retrofit.create(VideosApi::class.java)
    }

    @VideosScope
    @Provides
    fun getRepository(videosApi: VideosApi, vPlayerDatabase: VPlayerDatabase): VPlayerRepository {
        return VPlayerRepository(videosApi, vPlayerDatabase)
    }

    @VideosScope
    @Provides
    fun getPresenter(vPlayerRepository: VPlayerRepository): VideosContract.VideoPresenter =
        VideosPresenter(vPlayerRepository)

    @Provides
    @VideosScope
    fun provideDatabase(context: Context): VPlayerDatabase {
        return Room.databaseBuilder(context, VPlayerDatabase::class.java, "Vplayer_app_database.db")
            .allowMainThreadQueries()
            .build()
    }

    @VideosScope
    @Provides
    fun getPlayerPresenter(vPlayerRepository: VPlayerRepository): PlayerContract.Presenter =
        PlayerPresenter(vPlayerRepository)

    @Provides
    fun provideApplication(application: Application): Context {
        return application.applicationContext
    }

}