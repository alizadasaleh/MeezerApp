package ufaz.az.meezer.ui.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ufaz.az.meezer.R
import ufaz.az.meezer.data.local.database.AppDatabase
import ufaz.az.meezer.data.model.Quiz
import ufaz.az.meezer.data.repository.QuizDao

class QuizListFragment : Fragment() {
    private lateinit var quizDao: QuizDao
    private lateinit var quizAdapter: ArrayAdapter<Quiz>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_quiz_list, container, false)
        val listView: ListView = view.findViewById(R.id.list_view_quizzes)
        val buttonAddQuiz: Button = view.findViewById(R.id.button_add_quiz)
        val frameLayout = activity?.findViewById<FrameLayout>(R.id.fragment_container)
        if (frameLayout != null) {
            frameLayout.visibility = View.VISIBLE
        }
        val appDatabase =
            AppDatabase.getInstance(requireContext()) // Add this method in AppDatabase
        quizDao = appDatabase.quizDao()

        quizAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = quizAdapter

        lifecycleScope.launch {
            quizDao.getAllQuizzes().collect { quizzes ->
                quizAdapter.clear()
                quizAdapter.addAll(quizzes)
            }
        }

        buttonAddQuiz.setOnClickListener {
            AddQuizDialogFragment().show(parentFragmentManager, "AddQuizDialog")
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val quiz = quizAdapter.getItem(position)
            val fragment = QuizDetailsFragment.newInstance(quiz!!.id)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val frameLayout = activity?.findViewById<FrameLayout>(R.id.fragment_container)
        if (frameLayout != null) {
            frameLayout.visibility = View.GONE
        }
    }
}
