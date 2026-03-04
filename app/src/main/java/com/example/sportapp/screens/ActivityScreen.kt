package com.example.sportapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sportapp.viewmodels.ActivityViewModel
import com.example.sportapp.widgets.ActivityPageContent
import com.example.sportapp.widgets.SimpleTopAppBar

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ActivityScreen(navController: NavController, activityViewModel: ActivityViewModel) {

    Scaffold(
        topBar = {
            val newActivity = activityViewModel.allActivities
            val amount = newActivity.value.count() +1
            SimpleTopAppBar(title = "Aktivität ", gesamtpunkte = "${amount}")
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