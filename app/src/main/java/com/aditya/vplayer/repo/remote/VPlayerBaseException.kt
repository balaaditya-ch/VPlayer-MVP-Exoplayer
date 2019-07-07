package com.aditya.vplayer.repo.remote

/*
 * Created by suresh-zutk91 on 19/03/18.
 */

class VPlayerBaseException(detail: String, errorCode: Int, cause: Throwable?) : Exception(detail, cause) {
    var root: Throwable? = cause
    var details: String = detail
    var errorCode: Int = errorCode
}