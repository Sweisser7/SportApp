package com.example.sportapp.navigation


sealed class Screen(val route: String) {
    object MainPageScreen : Screen("mainpage")
    object AchievementScreen : Screen("achievement")
    object HistoryScreen : Screen("history")
    object ActivityScreen : Screen("activity")
}