package ufaz.az.meezer.data.repository

import kotlinx.coroutines.flow.Flow
import ufaz.az.meezer.data.model.Quiz
import javax.inject.Inject

class QuizRepository @Inject constructor(private val quizDao: QuizDao) {
    fun getAllQuizzes(): Flow<List<Quiz>> = quizDao.getAllQuizzes()

    fun getQuizzesForPlaylist(playlistId: Long): Flow<List<Quiz>> =
        quizDao.getQuizzesForPlaylist(playlistId)

    suspend fun createQuiz(quiz: Quiz): Long = quizDao.createQuiz(quiz)

    suspend fun deleteQuiz(quiz: Quiz) = quizDao.deleteQuiz(quiz)
}
