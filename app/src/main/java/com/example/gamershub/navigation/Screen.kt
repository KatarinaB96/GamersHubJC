package com.example.gamershub.navigation


sealed class Screen(val route: String) {
    data object SignInScreen : Screen("SignInScreen")
    data object GenreScreen : Screen("GenreScreen")
    data object GamesScreen : Screen("GamesScreen")
    data object GameDetailsScreen : Screen("GameDetailsScreen")
    data object SettingsScreen : Screen("SettingsScreen")
}