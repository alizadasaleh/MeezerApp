package ufaz.az.meezer.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ufaz.az.meezer.R
import ufaz.az.meezer.data.local.database.AppDatabase
import ufaz.az.meezer.data.repository.PlaylistDao
import ufaz.az.meezer.data.repository.QuizDao
import javax.inject.Inject

class QuizDetailsFragment : Fragment() {
    @Inject
    lateinit var playlistDao: PlaylistDao

    @Inject
    lateinit var quizDao: QuizDao

    companion object {
        private const val ARG_QUIZ_ID = "quizId"

        fun newInstance(quizId: Long): QuizDetailsFragment {
            val fragment = QuizDetailsFragment()
            val args = Bundle().apply { putLong(ARG_QUIZ_ID, quizId) }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz_detail, container, false)

        val quizNameText: TextView = view.findViewById(R.id.text_quiz_name)
        val playlistNameText: TextView = view.findViewById(R.id.text_playlist_name)
        val startQuizButton: Button = view.findViewById(R.id.button_start_quiz)

        val quizId = arguments?.getLong(ARG_QUIZ_ID) ?: return null


        lifecycleScope.launch {
            val quiz = quizDao.getQuiz(quizId)
            val playlist = playlistDao.getPlaylist(quiz.playlistId)

            quizNameText.text = quiz.name
            playlistNameText.text = playlist.name
        }

        startQuizButton.setOnClickListener {
            // Implement quiz start logic
        }

        return view
    }
}
