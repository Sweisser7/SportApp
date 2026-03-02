package com.example.sportapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sportapp.viewmodels.AchievementViewModel
import com.example.sportapp.viewmodels.MainViewModel
import com.example.sportapp.widgets.AchievementPageContent
import com.example.sportapp.widgets.MainPageContent
import com.example.sportapp.widgets.SimpleBottomAppBar
import com.example.sportapp.widgets.SimpleTopAppBar

@Composable
fun AchievementScreen(navController: NavController, achievementViewModel: AchievementViewModel) {
    Scaffold(
        topBar = {
            Column() {
                SimpleTopAppBar(title = "Erfolge")
                Text(text = "Gesamtpunkte: " + achievementViewModel.totalPoints.toString())
            }

        },
        bottomBar = {
            SimpleBottomAppBar(navController = navController)

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            AchievementPageContent(modifier = Modifier.padding(innerPadding))

        }
    }
}