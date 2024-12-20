package ufaz.az.meezer.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ufaz.az.meezer.ui.detail.DetailsScreen
import ufaz.az.meezer.ui.playlist.PlaylistScreen
import ufaz.az.meezer.ui.quiz.QuizListFragment
import ufaz.az.meezer.ui.quiz.QuizScreen
import ufaz.az.meezer.ui.search.SearchScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Search.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Playlist.route) {
                PlaylistScreen(
                    onTrackClick = { trackId ->
                        navController.navigate("detail/$trackId") {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
            composable(Screen.Search.route) {
                SearchScreen(navController)
            }
            composable(Screen.Quiz.route) {
                QuizScreen()
            }
            composable("quizFragment") {
                QuizListFragment() // This fragment will be shown
            }
            composable(Screen.Detail.route) { backStackEntry ->
                val trackId =
                    backStackEntry.arguments?.getString("trackId") // Pass the unique Deezer track ID
                DetailsScreen(trackId = trackId)
            }

        }
    }

}
