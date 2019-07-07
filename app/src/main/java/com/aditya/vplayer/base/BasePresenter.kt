package com.aditya.vplayer.base

interface BasePresenter<T> {

    fun attachview(view: T)
    fun detachView()
}