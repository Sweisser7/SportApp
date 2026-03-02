package com.example.sportapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sportapp.storage.Activity
import com.example.sportapp.viewmodels.ActivityViewModel
import com.example.sportapp.viewmodels.HistoryViewModel
import com.example.sportapp.viewmodels.MainViewModel
import com.example.sportapp.widgets.ActivityPageContent
import com.example.sportapp.widgets.MainPageContent
import com.example.sportapp.widgets.SimpleBottomAppBar
import com.example.sportapp.widgets.SimpleTopAppBar

@Composable
fun ActivityScreen(navController: NavController, activityViewModel: ActivityViewModel) {

    Scaffold(
        topBar = {
            SimpleTopAppBar(title = "Aktivität", gesamtpunkte = "")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ActivityPageContent(modifier = Modifier.padding(innerPadding),
                navController = navController,
                activityViewModel = activityViewModel,
                activity = activityViewModel.allActivities.collectAsState().value)

        }
    }
}