package com.pubscale.basicvideoplayer.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pubscale.basicvideoplayer.data.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the video player screen.
 * Fetches video URL from repository and exposes it to the UI.
 */
@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val repository: VideoRepository
) : ViewModel() {

    // Holds the video URL to be played
    private val _videoUrl = MutableLiveData<String>()
    val videoUrl: LiveData<String> = _videoUrl

    // Indicates whether loading is in progress
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        fetchVideoUrl()
    }

    // Fetch video URL from the repository
    fun fetchVideoUrl() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                
                val url = repository.getVideoUrl()
                _videoUrl.value = url
                
            } catch (e: Exception) {
                // Repository should handle all errors, but just in case
            } finally {
                _isLoading.value = false
            }
        }
    }
} 