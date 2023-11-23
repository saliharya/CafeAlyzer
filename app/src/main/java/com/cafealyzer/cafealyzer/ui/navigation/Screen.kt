package com.cafealyzer.cafealyzer.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object History : Screen("history")
    object Profile : Screen("profile")
    object Maps : Screen("maps")
}