package ufaz.az.meezer.ui.quiz

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import ufaz.az.meezer.R
import ufaz.az.meezer.ui.MainActivity

@Composable
fun QuizScreen() {
    val context = LocalContext.current

    // Ensure the activity is of the correct type
    if (context is MainActivity) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Quiz Screen", style = MaterialTheme.typography.titleLarge)

            Button(onClick = {
                // Switch to Fragment using FragmentManager
                context.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, QuizListFragment())
                    .addToBackStack(null)
                    .commit()
            }) {
                Text("Go to Quiz Fragment")
            }
        }
    } else {
        Text("Error: Invalid context. Please ensure you're in MainActivity.")
    }
}
