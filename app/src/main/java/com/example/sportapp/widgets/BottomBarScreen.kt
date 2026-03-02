package com.example.sportapp.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.sportapp.navigation.Screen

sealed class BottomBarScreen (
    val route: String,
    val title: String,
    val icon: ImageVector
){
    object MainPage: BottomBarScreen(
        route = Screen.MainPageScreen.route,
        title = "Startseite",
        icon = Icons.Filled.Home
    )

    object Achievements: BottomBarScreen(
        route = Screen.AchievementScreen.route,
        title = "Erfolge",
        icon = Icons.Filled.Star
    )

    object History: BottomBarScreen(
        route = Screen.HistoryScreen.route,
        title = "Verlauf",
        icon = Icons.Filled.AccountCircle
    )

}