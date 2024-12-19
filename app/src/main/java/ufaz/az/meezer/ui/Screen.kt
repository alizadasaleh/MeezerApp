package ufaz.az.meezer.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Playlist : Screen("playlist", "Playlist", Icons.Default.Favorite)
    object Search : Screen("search", "Search", Icons.Default.Search)
    object Quiz : Screen("quiz", "Quiz", Icons.Default.Menu)
    object Detail : Screen("detail/{trackId}", "Detail", Icons.Default.Face)

}

