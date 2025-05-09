package com.pubscale.basicvideoplayer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pubscale.basicvideoplayer.ui.player.VideoPlayerActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * Entry point of the application.
 * Simply launches the VideoPlayerActivity and finishes.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startVideoPlayer()
    }
    
    // Launch the video player activity
    private fun startVideoPlayer() {
        val intent = Intent(this, VideoPlayerActivity::class.java)
        startActivity(intent)
        finish()
    }
}