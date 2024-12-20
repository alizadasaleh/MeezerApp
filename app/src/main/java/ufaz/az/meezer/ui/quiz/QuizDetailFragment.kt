package ufaz.az.meezer.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ufaz.az.meezer.R
import ufaz.az.meezer.data.local.database.AppDatabase
import ufaz.az.meezer.data.repository.QuizDao

class QuizDetailsFragment : Fragment() {
    private lateinit var quizDao: QuizDao

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

        // Initialize quizDao manually
        val appDatabase = AppDatabase.getInstance(requireContext())
        quizDao = appDatabase.quizDao()

        // Rest of your code...

        val quizNameText: TextView = view.findViewById(R.id.text_quiz_name)
        val startQuizButton: Button = view.findViewById(R.id.button_start_quiz)

        val quizId = requireArguments().getLong(ARG_QUIZ_ID)

        lifecycleScope.launch {
            val quiz = withContext(Dispatchers.IO) {
                quizDao.getQuiz(quizId)
            }

            quizNameText.text = quiz.name
        }

        startQuizButton.setOnClickListener {
            // Implement quiz start logic
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val frameLayout = activity?.findViewById<FrameLayout>(R.id.fragment_container)
        if (frameLayout != null) {
            // Remove the FrameLayout from your activity layout here (consider using the parent view)
        }
    }
}