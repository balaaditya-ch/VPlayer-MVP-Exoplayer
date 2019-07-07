package com.aditya.vplayer.ui.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aditya.vplayer.R
import com.aditya.vplayer.models.VideoDetails
import com.aditya.vplayer.util.MarginItemDecorator
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.video_fragment.*
import javax.inject.Inject

class VideosFragment : DaggerFragment(), VideosContract.View {

    @Inject
    lateinit var presenter: VideosContract.VideoPresenter
    private lateinit var adapter: VideosAdapter


    override fun ShowVideos(videos: ArrayList<VideoDetails>) {
        adapter.add(videos)
        hideProgress()
    }

    override fun showError(message: String) {
        hideProgress()
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showProgress() {
        (activity as VideosActivity).showProgress()
    }

    override fun hideProgress() {
        (activity as VideosActivity).hideProgress()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.video_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress()
        initializeView()
        presenter.attachview(this@VideosFragment)
        presenter.fetchVideos()
    }

    private fun initializeView() {
        videos.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        videos.addItemDecoration(MarginItemDecorator(20))
        adapter = VideosAdapter(OnClickItem)
        videos.adapter = adapter
    }

    private val OnClickItem: (VideoDetails) -> Unit = { i ->
        (activity as OpenHelper).OnPlayVideo(i)
    }


}