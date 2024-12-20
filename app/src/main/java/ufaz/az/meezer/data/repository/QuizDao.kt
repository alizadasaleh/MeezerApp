package ufaz.az.meezer.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ufaz.az.meezer.data.model.Quiz

@Dao
interface QuizDao {
    @Query("SELECT * FROM quizzes ORDER BY createdAt DESC")
    fun getAllQuizzes(): Flow<List<Quiz>>

    @Query("SELECT * FROM quizzes where id = :quizId")
    fun getQuiz(quizId: Long): Quiz

    @Query("SELECT * FROM quizzes WHERE playlistId = :playlistId")
    fun getQuizzesForPlaylist(playlistId: Long): Flow<List<Quiz>>

    @Insert
    suspend fun createQuiz(quiz: Quiz): Long

    @Delete
    suspend fun deleteQuiz(quiz: Quiz)
}
