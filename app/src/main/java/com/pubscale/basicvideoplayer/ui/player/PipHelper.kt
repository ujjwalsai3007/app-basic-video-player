package com.pubscale.basicvideoplayer.ui.player

import android.app.Activity
import android.app.PictureInPictureParams
import android.os.Build
import android.util.Rational
import android.view.View
import androidx.media3.common.Player
import javax.inject.Inject

/**
 * Helper class to handle Picture-in-Picture mode functionality.
 * Centralizes PiP logic to keep the activity code clean.
 */
class PipHelper @Inject constructor() {
    
    /**
     * Attempts to enter PiP mode if the device supports it.
     * 
     * @param activity The activity that should enter PiP mode
     * @param playerView The view showing the video
     * @param player The ExoPlayer instance
     * @return wasPlaying True if the player was playing when PiP mode was entered
     */
    fun enterPipMode(activity: Activity, playerView: View?, player: Player?): Boolean {
        val isVideoReady = player?.playbackState == Player.STATE_READY
        val wasPlaying = player?.isPlaying == true
        
        if (isVideoReady && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Set 16:9 aspect ratio if we can't get the actual dimensions
            val aspectRatio = Rational(playerView?.width ?: 16, playerView?.height ?: 9)
            
            val pipParamsBuilder = PictureInPictureParams.Builder()
                .setAspectRatio(aspectRatio)
            
            activity.enterPictureInPictureMode(pipParamsBuilder.build())
            return wasPlaying
        }
        
        return false
    }
    
    /**
     * Handles UI and playback changes when PiP mode changes.
     * Shows/hides controls and manages playback state.
     */
    fun handlePipModeChanged(
        isInPipMode: Boolean,
        player: Player?,
        showControls: (Boolean) -> Unit,
        wasPlaying: Boolean
    ) {
        // Hide player controls in PiP mode
        showControls(!isInPipMode)
        
        // When exiting PiP mode, restore the previous playback state
        if (!isInPipMode) {
            if (wasPlaying) {
                player?.play()
            } else {
                player?.pause()
            }
        }
    }
} 