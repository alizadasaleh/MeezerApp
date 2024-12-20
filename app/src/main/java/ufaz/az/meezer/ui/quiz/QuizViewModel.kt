package ufaz.az.meezer.ui.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ufaz.az.meezer.data.model.Quiz
import ufaz.az.meezer.data.repository.QuizDao
import javax.inject.Inject

@HiltViewModel
class QuizListViewModel @Inject constructor(
    private val quizDao: QuizDao
) : ViewModel() {
    val quizzes: LiveData<List<Quiz>> = quizDao.getAllQuizzes().asLiveData()
}
