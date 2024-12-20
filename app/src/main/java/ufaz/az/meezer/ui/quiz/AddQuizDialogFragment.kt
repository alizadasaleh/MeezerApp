package ufaz.az.meezer.ui.quiz

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ufaz.az.meezer.R
import ufaz.az.meezer.data.local.database.AppDatabase
import ufaz.az.meezer.data.model.Playlist
import ufaz.az.meezer.data.model.Quiz

class AddQuizDialogFragment : DialogFragment() {

    private lateinit var playlistAdapter: PlaylistAdapter
    private var selectedPlaylistId: Long = 0L
// Track the selected playlist ID

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity().layoutInflater.inflate(R.layout.dialog_add_quiz, null)
        val quizNameEditText: EditText = view.findViewById(R.id.edit_quiz_name)
        val randomCheckBox: CheckBox = view.findViewById(R.id.checkbox_random)
        val saveButton: Button = view.findViewById(R.id.button_save_quiz)
        val playlistRecyclerView: RecyclerView = view.findViewById(R.id.playlist_recycler_view)  // Access the RecyclerView


        // Get playlists from database
        lifecycleScope.launch {
            val playlists = AppDatabase.getInstance(requireContext()).playlistDao().getAllPlaylists().first()
            setupPlaylistRecyclerView(playlists,playlistRecyclerView)
        }

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)

        saveButton.setOnClickListener {
            val name = quizNameEditText.text.toString()
            val isRandom = randomCheckBox.isChecked

            if (selectedPlaylistId == 0L) {
                // Handle case where no playlist is selected (optional: show a toast)
            } else {
                val quiz = Quiz(name = name, playlistId = selectedPlaylistId, isRandomSelection = isRandom)
                lifecycleScope.launch {
                    AppDatabase.getInstance(requireContext()).quizDao().createQuiz(quiz)
                    dismiss()
                }
            }
        }

        return builder.create()
    }

    private fun setupPlaylistRecyclerView(playlists: List<Playlist>, recyclerView: RecyclerView) {
        playlistAdapter = PlaylistAdapter(playlists) { playlist ->
            selectedPlaylistId = playlist.id
        }
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = playlistAdapter
        recyclerView.layoutManager = layoutManager
    }
}