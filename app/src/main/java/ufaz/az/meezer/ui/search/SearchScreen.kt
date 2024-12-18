package ufaz.az.meezer.ui.search

import androidx.compose.foundation.layout.*
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
import ufaz.az.meezer.ui.search.SearchViewModel

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    val viewModel: SearchViewModel = viewModel()

    // States for UI inputs
    var query by remember { mutableStateOf("") }
    var isSearchingForTracks by remember { mutableStateOf(true) } // Toggle: Tracks or Albums

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
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
            Button(onClick = { isSearchingForTracks = true }) {
                Text("Search Tracks")
            }
            Button(onClick = { isSearchingForTracks = false }) {
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
    }
}
