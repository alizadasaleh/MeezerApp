package ufaz.az.meezer.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ufaz.az.meezer.data.model.Quiz
import ufaz.az.meezer.data.repository.QuizDao
import ufaz.az.meezer.data.repository.QuizRepository
import javax.inject.Inject

@HiltViewModel
class QuizListViewModel @Inject constructor(
    private val quizDao: QuizDao
) : ViewModel() {
    val quizzes: LiveData<List<Quiz>> = quizDao.getAllQuizzes().asLiveData()
}
