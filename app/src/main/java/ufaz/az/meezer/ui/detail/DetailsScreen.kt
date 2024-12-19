package ufaz.az.meezer.ui.detail

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage


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
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Album artwork
                track.album.cover?.let { TrackImage(it) }

                // Track title
                Text("Title: ${track.title}", style = MaterialTheme.typography.headlineMedium)

                // Artist and album
                Text(
                    "Artist: ${track.artist.name}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    "Album: ${track.album.title}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 2.dp)
                )

                // Track additional details
                Text(
                    "Duration: ${track.duration}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    "Release Date: ${track.album.release_date}",
                    style = MaterialTheme.typography.bodyMedium
                )


                // Preview button
                Button(
                    onClick = {
                        viewModel.playTrack(track.preview)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Play Preview")
                }

                // Share button
                OutlinedButton(
                    onClick = {
                        // Handle share logic

                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Share Track")
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

// Helper function to format track duration in minutes and seconds
fun formatDuration(durationInSeconds: Int): String {
    val minutes = durationInSeconds / 60
    val seconds = durationInSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}

// Composable for the album artwork image
@Composable
fun TrackImage(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Album cover",
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface),
        contentScale = ContentScale.Crop
    )
}


