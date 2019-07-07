package com.aditya.vplayer.core

import com.aditya.vplayer.di.DaggerCoreComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class VPlayerApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {

        return DaggerCoreComponent.builder()
            .application(this)
            .build()
    }
}