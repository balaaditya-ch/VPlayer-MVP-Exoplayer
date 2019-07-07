package com.aditya.vplayer.di

import com.aditya.vplayer.di.videos.VideosBuilderModule
import com.aditya.vplayer.di.videos.VideosModule
import com.aditya.vplayer.di.videos.VideosScope
import com.aditya.vplayer.ui.player.PlayerActivity
import com.aditya.vplayer.ui.videos.VideosActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {


    @VideosScope
    @ContributesAndroidInjector(
        modules = [VideosModule::class,
            VideosBuilderModule::class]
    )
    abstract fun videosActivity(): VideosActivity

    @VideosScope
    @ContributesAndroidInjector(
        modules = [VideosModule::class,
            VideosBuilderModule::class]
    )
    abstract fun playerActivity(): PlayerActivity

}