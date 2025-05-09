package com.pubscale.basicvideoplayer.data.api

import android.content.Context
import com.google.gson.Gson
import com.pubscale.basicvideoplayer.data.model.VideoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Data source for reading video information from the JSON file.
 * Uses app context to access assets folder.
 */
class JsonDataSource @Inject constructor(
    private val context: Context,
    private val gson: Gson
) {
    
    companion object {
        private const val JSON_FILE_NAME = "video_url.json"
    }
    
    /**
     * Reads the JSON file from assets and converts it to VideoResponse.
     * Uses IO dispatcher to avoid blocking the main thread.
     */
    suspend fun getVideoUrl(): VideoResponse {
        return withContext(Dispatchers.IO) {
            val jsonString = context.assets.open(JSON_FILE_NAME).bufferedReader().use { it.readText() }
            gson.fromJson(jsonString, VideoResponse::class.java)
        }
    }
} 