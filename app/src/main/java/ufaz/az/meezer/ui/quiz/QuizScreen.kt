package ufaz.az.meezer.ui.quiz

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.hilt.navigation.compose.hiltViewModel
import ufaz.az.meezer.data.model.Playlist
import ufaz.az.meezer.data.model.Quiz
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import ufaz.az.meezer.ui.detail.DetailsViewModel


@Composable
fun QuizScreen(
    viewModel: QuizViewModel = hiltViewModel(),
    onQuizClick: (Quiz) -> Unit
) {
    val quizzes by viewModel.quizzes.collectAsState(initial = emptyList())
    val viewModel: QuizViewModel = hiltViewModel()
    var showCreateDialog by remember { mutableStateOf(false) }
    var quizName by remember { mutableStateOf("") }
    var selectedPlaylistId by remember { mutableStateOf<Long?>(null) }
    var isRandom by remember { mutableStateOf(false) }
    val playlists by viewModel.playlists.collectAsState(initial = emptyList())
    val coroutineScope = rememberCoroutineScope()


    Column(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = { showCreateDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Create New Quiz")
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(quizzes) { quiz ->
                    QuizItem(
                        quiz = quiz,
                        onClick = { onQuizClick(quiz) },
                        onDelete = { coroutineScope.launch {
                            viewModel.deleteQuiz(quiz)
                        } }
                    )
                }
            }
        }

        if (showCreateDialog) {
            AlertDialog(
                onDismissRequest = { showCreateDialog = false },
                title = { Text("Create New Quiz") },
                text = {
                    Column {
                        TextField(
                            value = quizName,
                            onValueChange = { quizName = it },
                            label = { Text("Quiz Name") }
                        )
                        LazyColumn {
                            items(playlists) { playlist ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedPlaylistId = playlist.id }
                                ) {
                                    RadioButton(
                                        selected = selectedPlaylistId == playlist.id,
                                        onClick = { selectedPlaylistId = playlist.id }
                                    )
                                    Text(playlist.name)
                                }
                            }
                        }
                        Row {
                            Checkbox(
                                checked = isRandom,
                                onCheckedChange = { isRandom = it }
                            )
                            Text("Random Questions")
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            selectedPlaylistId?.let { playlistId ->
                                coroutineScope.launch {
                                    viewModel.createQuiz(quizName, playlistId, isRandom)
                                }
                            }
                            showCreateDialog = false
                        }
                    ) {
                        Text("Create")
                    }
                },
                dismissButton = {
                    Button(onClick = { showCreateDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }

    @Composable
    fun QuizItem(quiz: Quiz, onClick: () -> Unit, onDelete: () -> Unit) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable(onClick = onClick),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = quiz.name, style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Quiz")
                }
            }
        }
    }

