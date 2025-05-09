package com.pubscale.basicvideoplayer.data.repository

import com.pubscale.basicvideoplayer.data.api.JsonDataSource
import com.pubscale.basicvideoplayer.data.model.VideoResponse
import javax.inject.Inject

/**
 * Repository that provides video URL data.
 * Handles fetching from JSON and provides fallback if needed.
 */
class VideoRepository @Inject constructor(
    private val jsonDataSource: JsonDataSource
) {
    
    /**
     * Gets the video URL from local JSON file or returns fallback URL.
     * Ensures a valid URL is always returned.
     */
    suspend fun getVideoUrl(): String {
        return try {
            val response = jsonDataSource.getVideoUrl()
            
            // Return URL if valid, otherwise use fallback
            response.url.takeIf { it.isNotEmpty() }
                ?: VideoResponse.FALLBACK_VIDEO_URL
        } catch (e: Exception) {
            // On any error, return the fallback URL
            VideoResponse.FALLBACK_VIDEO_URL
        }
    }
} 