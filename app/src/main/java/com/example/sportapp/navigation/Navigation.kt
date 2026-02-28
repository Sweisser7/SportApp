package com.example.sportapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sportapp.screens.AchievementScreen
import com.example.sportapp.screens.ActivityScreen
import com.example.sportapp.screens.HistoryScreen
import com.example.sportapp.screens.MainPageScreen
import com.example.sportapp.viewmodels.ActivityViewModel
import com.example.sportapp.viewmodels.HistoryViewModel
import com.example.sportapp.viewmodels.Injector
import com.example.sportapp.viewmodels.MainViewModel

@Composable
fun Navigation() {

    val navController = rememberNavController() // create a NavController instance

    val activityViewModel: ActivityViewModel = viewModel(factory = Injector.provideModelFactory(context = LocalContext.current))
    val historyViewModel: HistoryViewModel = viewModel(factory = Injector.provideModelFactory(context = LocalContext.current))
    val mainViewModel: MainViewModel = viewModel(factory = Injector.provideModelFactory(context = LocalContext.current))



    NavHost(navController = navController, // pass the NavController to NavHost
        startDestination = Screen.MainPageScreen.route) {  // pass a start destination
        composable(route = Screen.MainPageScreen.route){   // route with name "homescreen" navigates to HomeScreen composable
            MainPageScreen(navController = navController, mainViewModel = mainViewModel)
        }

        composable(route = Screen.AchievementScreen.route){
            AchievementScreen(navController = navController, mainViewModel = mainViewModel)
        }

        composable(route = Screen.HistoryScreen.route){
            HistoryScreen(navController = navController, historyViewModel = historyViewModel, mainViewModel = mainViewModel)
        }
        composable(route = Screen.ActivityScreen.route) {
            ActivityScreen(navController = navController, activityViewModel = activityViewModel, historyViewModel = historyViewModel, mainViewModel = mainViewModel)
        }
    }

}