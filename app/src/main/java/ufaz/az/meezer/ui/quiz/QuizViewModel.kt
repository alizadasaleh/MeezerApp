package ufaz.az.meezer.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ufaz.az.meezer.data.model.Playlist
import ufaz.az.meezer.data.model.Quiz
import ufaz.az.meezer.data.repository.PlaylistDao
import ufaz.az.meezer.data.repository.QuizDao
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizDao: QuizDao,
    private val playlistDao: PlaylistDao
) : ViewModel() {
    // Flow of playlists fetched from the DAO
    val playlists: Flow<List<Playlist>> = playlistDao.getAllPlaylists()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    val quizzes: Flow<List<Quiz>> = quizDao.getAllQuizzes()

    private val _selectedQuiz = MutableStateFlow<Quiz?>(null)
    val selectedQuiz: StateFlow<Quiz?> = _selectedQuiz

    fun selectQuiz(quizId: Long) {
        viewModelScope.launch {
            _selectedQuiz.value = quizDao.getQuizById(quizId)
        }
    }

    suspend fun createQuiz(name: String, playlistId: Long, isRandom: Boolean) {
        val quiz = Quiz(name = name, playlistId = playlistId, isRandom = isRandom)
        quizDao.createQuiz(quiz)
    }

    suspend fun deleteQuiz(quiz: Quiz) {
        quizDao.deleteQuiz(quiz)
    }
}
