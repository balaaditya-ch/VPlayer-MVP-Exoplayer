package com.aditya.vplayer.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.aditya.vplayer.R
import com.aditya.vplayer.core.addFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

open class BaseActivity : DaggerAppCompatActivity(), BaseInteractions {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.INVISIBLE

    }

    fun replace(fragment: Fragment) {
        addFragment(fragment, R.id.content)
    }
}