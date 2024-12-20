package ufaz.az.meezer.ui.quiz

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ufaz.az.meezer.R
import ufaz.az.meezer.data.local.database.AppDatabase
import ufaz.az.meezer.data.model.Quiz

class AddQuizDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_add_quiz, null)
        val quizNameEditText: EditText = view.findViewById(R.id.edit_quiz_name)
        val playlistIdEditText: EditText = view.findViewById(R.id.edit_playlist_id)
        val randomCheckBox: CheckBox = view.findViewById(R.id.checkbox_random)
        val saveButton: Button = view.findViewById(R.id.button_save_quiz)

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)

        saveButton.setOnClickListener {
            val name = quizNameEditText.text.toString()
            val playlistId = playlistIdEditText.text.toString().toLong()
            val isRandom = randomCheckBox.isChecked

            lifecycleScope.launch {
                val quiz = Quiz(name = name, playlistId = playlistId, isRandomSelection = isRandom)
                AppDatabase.getInstance(requireContext()).quizDao().createQuiz(quiz)
                dismiss()
            }
        }

        return builder.create()
    }
}
