package com.cafealyzer.cafealyzer.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Login : Screen("login")
    object Register : Screen("register")
    object History : Screen("history")
    object Profile : Screen("profile")
    object Maps : Screen("maps")
    object HistoryDetail : Screen("history_detail")
}