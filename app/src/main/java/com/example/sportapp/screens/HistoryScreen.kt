package com.example.sportapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sportapp.viewmodels.ActivityViewModel
import com.example.sportapp.viewmodels.HistoryViewModel
import com.example.sportapp.viewmodels.MainViewModel
import com.example.sportapp.widgets.HistoryPageContent
import com.example.sportapp.widgets.MainPageContent
import com.example.sportapp.widgets.SimpleBottomAppBar
import com.example.sportapp.widgets.SimpleTopAppBar

@Composable
fun HistoryScreen(navController: NavController, historyViewModel: HistoryViewModel) {
    Scaffold(
        topBar = {
            val stats by historyViewModel.totalPoints.collectAsState()
            Column() {
                SimpleTopAppBar(title = "Verlauf")
                Text(text = "Gesamtpunkte: ${stats?.totalPoints ?: 0}")
            }
        },
        bottomBar = {
            SimpleBottomAppBar(navController = navController)

        }
    ) { innerPadding ->
            HistoryPageContent(
                modifier = Modifier.padding(innerPadding),
                historyviewModel = historyViewModel,
                activity = historyViewModel.allActivities.collectAsState().value,
                navController = navController
            )
    }
}