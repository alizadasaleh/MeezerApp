package ufaz.az.meezer.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ufaz.az.meezer.data.api.SearchResult
import ufaz.az.meezer.data.model.Playlist
import ufaz.az.meezer.ui.playlist.PlaylistViewModel

@Composable
fun SearchScreen(navController: NavHostController, searchViewModel: SearchViewModel = hiltViewModel(), playlistViewModel: PlaylistViewModel = hiltViewModel()) {
    val searchResults by searchViewModel.searchResults.collectAsState()
    val playlists by playlistViewModel.playlists.collectAsState(initial = emptyList())
    val viewModel: SearchViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope() // Scope for the SearchScreen
    var query by remember { mutableStateOf("") }
    var isSearchingForTracks by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedResult by remember { mutableStateOf<SearchResult?>(null) }


    if (showDialog && selectedResult != null) {
        AddToPlaylistDialog(
            onDismissRequest = { showDialog = false },
            onConfirm = { playlistId ->
                selectedResult?.let { result ->
                    selectedResult?.let { result ->
                        coroutineScope.launch { // Launch a coroutine in the *SearchScreen's* scope
                            playlistViewModel.addTrackToPlaylist(playlistId, result)
                            showDialog = false // Dismiss the dialog *after* the operation completes
                        }
                    }
                }
            },
            playlists = playlists
        )
    }

    Column(

        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Search Bar
        BasicTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                viewModel.search(query, isSearchingForTracks)
            }),
            decorationBox = { innerTextField ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.surface
                ) {
                    Box(modifier = Modifier.padding(16.dp)) {
                        if (query.isEmpty()) {
                            Text("Search...", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        innerTextField()
                    }
                }
            }
        )

        // Toggle Buttons for Search Type
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = { viewModel.search(query, true) },
                enabled = query.isNotEmpty()
            ) {
                Text("Search Tracks")
            }
            Button(
                onClick = { viewModel.search(query, false) },
                enabled = query.isNotEmpty()
            ) {
                Text("Search Albums")
            }
        }

        // Search Button
        Button(
            onClick = { viewModel.search(query, isSearchingForTracks) },
            enabled = query.isNotEmpty()
        ) {
            Text("Search")
        }

        // Placeholder for Results
        Text(
            "Search Results Will Appear Here!",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyMedium
        )
        // Search bar and buttons (same as before)

        // Display results
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(searchResults) { result ->
                SearchResultItem(result, onClick = {
                    navController.navigate("detail/${result.id}") {
                        launchSingleTop = true
                        restoreState = true
                    }
                }, onAddToPlaylist = {
                    selectedResult = result
                    showDialog = true
                })
            }
        }
    }
}

@Composable
fun SearchResultItem(result: SearchResult, onClick: () -> Unit, onAddToPlaylist: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = result.title, style = MaterialTheme.typography.titleMedium)
            Text(text = "Artist: ${result.artist.name}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Album: ${result.album.title}", style = MaterialTheme.typography.bodySmall)
            result.release_date?.let {
                Text(text = "Release Date: $it", style = MaterialTheme.typography.bodySmall)
            }
            result.explicit_lyrics?.let {
                Text(
                    text = "Explicit Lyrics: ${if (it) "Yes" else "No"}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(onClick = onAddToPlaylist) {
                Text("Add to Playlist")
            }
        }
    }
}

@Composable
fun AddToPlaylistDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (Long) -> Unit,
    playlists: List<Playlist>
) {
    var selectedPlaylistId by remember { mutableStateOf<Long?>(null) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Add to Playlist") },
        text = {
            Column {
                playlists.forEach { playlist ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedPlaylistId = playlist.id },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedPlaylistId == playlist.id,
                            onClick = { selectedPlaylistId = playlist.id }
                        )
                        Text(text = playlist.name)
                    }
                }
                if (playlists.isEmpty()) {
                    Text("No playlists available. Create one first.")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    selectedPlaylistId?.let { onConfirm(it) }
                },
                enabled = selectedPlaylistId != null
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}