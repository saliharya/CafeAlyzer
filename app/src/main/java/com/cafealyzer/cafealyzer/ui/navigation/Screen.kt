package com.cafealyzer.cafealyzer.ui.navigation

sealed class Screen(val route: String) {
    object Maps : Screen("maps")
    object TopCafe : Screen("topcafe")
    object Login : Screen("login")
    object Register : Screen("register")
    object History : Screen("history")
    object Profile : Screen("profile")
    object HistoryDetail : Screen("history_detail")
}