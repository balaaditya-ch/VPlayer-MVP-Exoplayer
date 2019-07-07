package com.aditya.vplayer.di.videos

import com.aditya.vplayer.ui.player.PlayerFragment
import com.aditya.vplayer.ui.videos.VideosFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
public abstract class VideosBuilderModule {

    @ContributesAndroidInjector
    abstract fun videosFragment(): VideosFragment

    @ContributesAndroidInjector
    abstract fun playerFragment(): PlayerFragment
}