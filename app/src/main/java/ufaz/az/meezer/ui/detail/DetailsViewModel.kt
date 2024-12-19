package ufaz.az.meezer.ui.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.MediaItem.Builder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ufaz.az.meezer.data.api.DeezerApiService
import ufaz.az.meezer.data.api.TrackDetailsResponse
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val deezerApiService: DeezerApiService
) : ViewModel() {

    private val _trackDetails = MutableStateFlow<TrackDetailsResponse?>(null)
    val trackDetails: StateFlow<TrackDetailsResponse?> = _trackDetails

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var exoPlayer: ExoPlayer? = null

    fun initializePlayer(context: Context) {
        exoPlayer = ExoPlayer.Builder(context).build()
    }

    fun releasePlayer() {
        exoPlayer?.release()
        exoPlayer = null
    }

    fun loadTrackDetails(trackId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val details = deezerApiService.getTrackDetails(trackId)
                _trackDetails.value = details
            } catch (e: Exception) {
                // Handle errors (e.g., log or show error UI)
                e.printStackTrace()
                _trackDetails.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun playTrack(previewUrl: String) {
        exoPlayer?.let { player ->
            try {
                // Log the URL for debugging
                println("Attempting to play: $previewUrl")

                // Build the MediaItem
                val mediaItem = MediaItem.fromUri(previewUrl)
                player.setMediaItem(mediaItem)

                // Prepare and start playback
                player.prepare()
                player.play()
            } catch (e: Exception) {
                // Log playback errors
                e.printStackTrace()
            }
        } ?: println("ExoPlayer is not initialized")
    }

}
