package com.aditya.vplayer.core

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {

    supportFragmentManager.beginTransaction().replace(frameId, fragment).commit()

}


fun <T> Context.openActivity(it: Class<T>, bundle: Bundle) {
    val intent = Intent(this, it)
    intent.putExtras(bundle)
    startActivity(intent)

}