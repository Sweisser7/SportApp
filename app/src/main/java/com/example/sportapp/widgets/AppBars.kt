package com.example.sportapp.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.room.util.TableInfo
import com.example.sportapp.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(
    title: String,
    navigationIcons: @Composable () -> Unit = {},
    mainViewModel: MainViewModel
){
    CenterAlignedTopAppBar(
        title = {
            Row(modifier = Modifier
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Text(title)
                Spacer(modifier = Modifier.weight(1f))
                Text("Gesampunkte: " + mainViewModel.totalPoints.toString())
            }},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        navigationIcon = navigationIcons
    )
}

@Composable
fun SimpleBottomAppBar(navController: NavController) {
    val screens = listOf(
        BottomBarScreen.MainPage,
        BottomBarScreen.Achievements,
        BottomBarScreen.History
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach { screen ->
            NavigationBarItem(label = { Text(screen.title) },
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                onClick = { navController.navigate(screen.route) },
                icon = { Icon(
                    imageVector = screen.icon,
                    contentDescription = screen.title
                )
                }
            )
        }
    }
}