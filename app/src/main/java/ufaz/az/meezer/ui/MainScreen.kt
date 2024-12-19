package ufaz.az.meezer.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ufaz.az.meezer.ui.playlist.PlaylistScreen
import ufaz.az.meezer.ui.quiz.QuizScreen
import ufaz.az.meezer.ui.search.Screen
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
                PlaylistScreen()
            }
            composable(Screen.Search.route) {
                SearchScreen(navController)
            }
            composable(Screen.Quiz.route) {
                QuizScreen()
            }
        }
    }
}
