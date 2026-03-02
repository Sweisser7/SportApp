package com.example.sportapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sportapp.viewmodels.MainViewModel
import com.example.sportapp.widgets.MainPageContent
import com.example.sportapp.widgets.MainPageContent
import com.example.sportapp.widgets.SimpleBottomAppBar
import com.example.sportapp.widgets.SimpleTopAppBar

@Composable
fun MainPageScreen(navController: NavController, mainViewModel: MainViewModel) {
    Scaffold(
        topBar = {
            val stats by mainViewModel.totalPoints.collectAsState()
            Column() {
                SimpleTopAppBar(title = "Sport App")
                Text(text = "Gesamtpunkte: ${stats?.totalPoints ?: 0}")
            }

        },
        bottomBar = {
            SimpleBottomAppBar (navController = navController)

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            MainPageContent(modifier = Modifier.padding(innerPadding), navController = navController, mainViewModel = mainViewModel)

        }
    }
}