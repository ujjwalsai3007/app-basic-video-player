package com.pubscale.basicvideoplayer.data.model

/**
 * Data class that represents the video information from JSON.
 * Contains the URL of the video to be played.
 */
data class VideoResponse(
    val url: String
) {
    companion object {
        // Fallback URL to use if JSON reading fails or returns empty URL
        const val FALLBACK_VIDEO_URL = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    }
} 