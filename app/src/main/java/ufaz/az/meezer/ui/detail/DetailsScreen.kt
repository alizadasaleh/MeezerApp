package ufaz.az.meezer.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailsScreen(title: String?, artist: String?, album: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Details Screen",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Title: $title",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Artist: $artist",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Album: $album",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
