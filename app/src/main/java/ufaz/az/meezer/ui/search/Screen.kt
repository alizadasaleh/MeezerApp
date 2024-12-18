package ufaz.az.meezer.ui.search

sealed class Screen(val route: String) {
    object Search : Screen("search")
}
