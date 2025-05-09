package com.pubscale.basicvideoplayer.ui.player

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.pubscale.basicvideoplayer.R
import com.pubscale.basicvideoplayer.ui.viewmodel.VideoPlayerViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Main activity that handles video playback and PiP functionality.
 * Observes the ViewModel to get video URL and manages player lifecycle.
 */
@AndroidEntryPoint
class VideoPlayerActivity : AppCompatActivity() {
    private var player: ExoPlayer? = null
    private var playerView: PlayerView? = null
    private lateinit var progressBar: ProgressBar
    private val viewModel: VideoPlayerViewModel by viewModels()
    private var isVideoReady = false
    private var wasPlayingBeforePip = false
    
    @Inject
    lateinit var pipHelper: PipHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
        
        playerView = findViewById(R.id.player_view)
        progressBar = findViewById(R.id.progress_bar)
        
        setupExoPlayer()
        setupObservers()
    }
    
    // Connect to ViewModel and observe changes
    private fun setupObservers() {
        viewModel.videoUrl.observe(this) { url ->
            if (url.isNotEmpty()) {
                loadRemoteVideo(url)
            }
        }
        
        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    // Initialize the ExoPlayer instance
    private fun setupExoPlayer() {
        player = ExoPlayer.Builder(this).build().apply {
            addListener(PlayerEventListener())
        }
        
        playerView?.player = player
        player?.playWhenReady = true
    }
    
    // Listen for player events to update UI and handle errors
    private inner class PlayerEventListener : Player.Listener {
        override fun onPlayerError(error: PlaybackException) {
            loadLocalFallbackVideo()
        }
        
        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                Player.STATE_READY -> {
                    isVideoReady = true
                    progressBar.visibility = View.GONE
                }
                Player.STATE_BUFFERING -> {
                    progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    // Load video from the URL provided by the ViewModel
    private fun loadRemoteVideo(videoUrl: String) {
        progressBar.visibility = View.VISIBLE
        try {
            val uri = Uri.parse(videoUrl)
            val mediaItem = MediaItem.fromUri(uri)
            
            player?.setMediaItem(mediaItem)
            player?.prepare()
            
            player?.addListener(object : Player.Listener {
                override fun onPlayerError(error: PlaybackException) {
                    player?.removeListener(this)
                    loadLocalFallbackVideo()
                }
                
                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_READY) {
                        progressBar.visibility = View.GONE
                        player?.play()
                        player?.removeListener(this)
                    }
                }
            })
        } catch (e: Exception) {
            loadLocalFallbackVideo()
        }
    }

    // Fallback to local video if remote loading fails
    private fun loadLocalFallbackVideo() {
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.sample_video)
        val mediaItem = MediaItem.fromUri(videoUri)
        player?.setMediaItem(mediaItem)
        player?.prepare()
        player?.play()
    }

    // Enter PiP mode when user navigates away from the app
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        wasPlayingBeforePip = player?.isPlaying == true
        pipHelper.enterPipMode(this, playerView, player)
    }

    // Handle PiP mode changes - show/hide controls and manage playback
    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean, 
        newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        
        pipHelper.handlePipModeChanged(
            isInPictureInPictureMode,
            player,
            { showControls -> playerView?.useController = showControls },
            wasPlayingBeforePip
        )
    }

    // Enter PiP mode when back button is pressed instead of exiting
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (isVideoReady) {
            wasPlayingBeforePip = player?.isPlaying == true
            pipHelper.enterPipMode(this, playerView, player)
        } else {
            super.onBackPressed()
        }
    }

    // Lifecycle management for ExoPlayer
    override fun onStart() {
        super.onStart()
        if (player == null) {
            setupExoPlayer()
        }
    }
    
    override fun onRestart() {
        super.onRestart()
        player?.play()
    }

    override fun onPause() {
        super.onPause()
        if (!isInPictureInPictureMode) {
            player?.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isInPictureInPictureMode) {
            player?.play()
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
    }

    private fun releasePlayer() {
        player?.release()
        player = null
    }
} 