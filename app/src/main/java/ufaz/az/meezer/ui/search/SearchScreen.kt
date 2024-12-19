package ufaz.az.meezer.ui.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ufaz.az.meezer.data.api.SearchResult

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun SearchScreen(navController: NavHostController, viewModel: SearchViewModel = hiltViewModel()) {
    val viewModel: SearchViewModel = viewModel()
    val searchResults by viewModel.searchResults.collectAsState()

    var query by remember { mutableStateOf("") }
    var isSearchingForTracks by remember { mutableStateOf(true) }

    Column(

        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Search Bar
        BasicTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
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
                SearchResultItem(result) {
                    // Navigate to details screen
                    navController.navigate("detail/${result.id}"){
                        launchSingleTop = true
                        restoreState = true
                    }

                }
            }
        }
    }
}

@Composable
fun SearchResultItem(result: SearchResult, onClick: () -> Unit) {
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
        }
    }
}