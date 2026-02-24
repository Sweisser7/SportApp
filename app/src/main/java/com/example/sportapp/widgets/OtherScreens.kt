package com.example.sportapp.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.sportapp.navigation.Screen

sealed class OtherScreens (
    val route: String,
    val title: String,
){
    object ActivityPage: OtherScreens(
        route = Screen.ActivityScreen.route,
        title = "activity"
    )
}