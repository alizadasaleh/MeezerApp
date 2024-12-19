package ufaz.az.meezer.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel



@Composable
fun DetailsScreen(trackId: String?) {
    val viewModel: DetailsViewModel = hiltViewModel()
    val trackDetails by viewModel.trackDetails.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Initialize and release ExoPlayer
    val context = LocalContext.current
    DisposableEffect(Unit) {
        viewModel.initializePlayer(context)
        onDispose {
            viewModel.releasePlayer()
        }
    }
    LaunchedEffect(trackId) {
        trackId?.let { viewModel.loadTrackDetails(it) }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        trackDetails?.let { track ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Display track details
                Text("Title: ${track.title}", style = MaterialTheme.typography.titleLarge)
                Text("Artist: ${track.artist.name}", style = MaterialTheme.typography.bodyMedium)
                Text("Album: ${track.album.title}", style = MaterialTheme.typography.bodySmall)

                // Add a Play Button
                Button(
                    onClick = {
                        viewModel.playTrack(track.preview)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Play Preview")
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Track details not available")
        }
    }
}

