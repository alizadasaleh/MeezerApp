package ufaz.az.meezer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizzes")
data class Quiz(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val playlistId: Long,
    val isRandomSelection: Boolean,
    val createdAt: Long = System.currentTimeMillis()
)
