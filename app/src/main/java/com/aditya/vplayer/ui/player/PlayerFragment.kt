package com.aditya.vplayer.ui.player

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aditya.vplayer.R
import com.aditya.vplayer.models.VideoDetails
import com.aditya.vplayer.ui.player.PlayerActivity.Companion.SELECTED
import com.aditya.vplayer.ui.player.PlayerActivity.Companion.UNSELECTED
import com.aditya.vplayer.util.MarginItemDecorator
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.player_fragment.*
import javax.inject.Inject

class PlayerFragment : DaggerFragment(), PlayerContract.View {

    private var currentWindow: Int = 0
    private var playWhenReady: Boolean = true
    private var playbackPosition: Long = 0

    var player: SimpleExoPlayer? = null

    private lateinit var videoDetails: VideoDetails
    private lateinit var suggestionsAdapter: SuggestionsAdapter

    @Inject
    lateinit var presenter: PlayerContract.Presenter

    override fun showProgress() {
        (activity as PlayerActivity).showProgress()
    }

    override fun hideProgress() {
        (activity as PlayerActivity).hideProgress()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.player_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgress()
        videoDetails = (activity as PlayerActivity).videoDetails
        viewBinds()
        initializeRelatedVideos()
        presenter.attachview(this@PlayerFragment)
        getSuggestionList()
    }


    override fun onResume() {
        super.onResume()
        initializeView()
        setPlayerListner()
        playVideo(presenter.getPlayback(videoDetails.id.toLong()))
    }

    private fun setPlayerListner() {

        player?.addListener(object : Player.DefaultEventListener() {

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)
                when (playbackState) {
                    Player.STATE_IDLE -> {
                    }
                    Player.STATE_BUFFERING -> {
                    }
                    Player.STATE_READY -> {
                    }
                    Player.STATE_ENDED -> {
                        setPlayback(videoDetails.id.toLong(), 0)
                        videoDetails.selected = UNSELECTED
                        suggestionsAdapter.playNext().let { details ->
                            if (videoDetails.id != details.id) {
                                videoDetails = details
                                playVideo(presenter.getPlayback(videoDetails.id.toLong()))
                                viewBinds()
                            }
                        }
                    }
                }
            }
        })
    }


    private fun initializeRelatedVideos() {
        suggestions.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        suggestions.addItemDecoration(MarginItemDecorator(20))
        suggestionsAdapter = SuggestionsAdapter(ClickItem)
        suggestions.adapter = suggestionsAdapter
    }

    private fun initializeView() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(context),
                DefaultTrackSelector(),
                DefaultLoadControl()
            )
            video_view.player = player
            video_view.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            player!!.playWhenReady = true
            player!!.seekTo(currentWindow, playbackPosition)
        }
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val userAgent = "exoplayer-codelab"
        return ExtractorMediaSource.Factory(DefaultHttpDataSourceFactory(userAgent)).createMediaSource(uri)
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun releasePlayer() {
        player?.let { simpleExoplayer ->
            playbackPosition = simpleExoplayer.getCurrentPosition()
            currentWindow = simpleExoplayer.getCurrentWindowIndex()
            playWhenReady = simpleExoplayer.getPlayWhenReady()
            Log.e("$playbackPosition  : PlaybackPosition ", "$currentWindow : Current Window")
            simpleExoplayer.release()
        }
        player = null
    }

    private val ClickItem: (VideoDetails, Int) -> Unit = { details, pos ->

        videoDetails.selected = UNSELECTED
        suggestionsAdapter.playSelected(pos).let {
            if (videoDetails.id != it.id) {
                setPlayback(videoDetails.id.toLong(), player?.currentPosition ?: 0)
                videoDetails = details
                playVideo(presenter.getPlayback(videoDetails.id.toLong()))
                viewBinds()
            }
        }
    }

    override fun getSuggestionList() {

        val list = presenter.getSuggestionList(videoDetails.id.toLong())
        val videos = ArrayList<VideoDetails>()

        list.forEach {
            val videoDetails = VideoDetails(
                description = it.description,
                id = it.id.toString(),
                thumb = it.thumb,
                title = it.title,
                url = it.url,
                selected = UNSELECTED
            )
            videos.add(videoDetails)
        }

        setSuggestionAdapter(videos)
        hideProgress()
//        list.subscribeOn(Schedulers.io()).map {
//            val videos = ArrayList<VideoDetails>()
//            it.forEach{
//                var videoDetails = VideoDetails(description = it.description,id = it.id.toString(),thumb = it.thumb,title = it.title,url = it.url,selected = UNSELECTED)
//                videos.add(videoDetails)
//            }
//            videos
//        }.observeOn(AndroidSchedulers.mainThread()).subscribe{
//            setSuggestionAdapter(it)
//        }

    }

    override fun setSuggestionAdapter(suggestionList: ArrayList<VideoDetails>) {
        videoDetails.selected = SELECTED
        suggestionList.run { add(0, videoDetails) }
        this.suggestionsAdapter.add(suggestionList)

    }

    override fun setPlayback(id: Long, playback: Long) {
        presenter.updatePlayback(id, playback)
    }

    override fun onPause() {
        super.onPause()
        if (player?.playbackState != Player.STATE_ENDED) {
            setPlayback(videoDetails.id.toLong(), player?.currentPosition ?: 0L)
        }
    }

    override fun viewBinds() {
        title.text = videoDetails.title
        description.text = this.videoDetails.description
    }

    override fun playVideo(playbackPosition: Long) {
        val mediaSource = buildMediaSource(Uri.parse(videoDetails.url))
        player!!.prepare(mediaSource, true, false)
        player!!.seekTo(currentWindow, playbackPosition)
    }

}

