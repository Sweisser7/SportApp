package com.example.sportapp.widgets

import android.location.Location
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sportapp.location.LocationManager
import com.example.sportapp.viewmodels.HistoryViewModel
import com.example.sportapp.storage.Activity
import com.example.sportapp.viewmodels.ActivityViewModel
import com.example.sportapp.viewmodels.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import java.util.Locale


@Composable
fun MainPageContent(modifier: Modifier, navController: NavController, mainViewModel: MainViewModel) {
    val screen = OtherScreens.ActivityPage
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
    }
    Spacer(modifier = Modifier)
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
//        Text(text = "Gesampunkte", fontSize = 20.sp)
//        Text(text = mainViewModel.totalPoints.toString(), fontSize = 30.sp)
    }

    Spacer(modifier = Modifier)
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        FilledTonalButton(onClick = {navController.navigate(screen.route) }) {
            Text(text = "Aktivität starten")
        }
    }

}

@Composable
fun AchievementPageContent(modifier: Modifier) {
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Hello World2")
    }

}

@Composable
fun HistoryPageContent(modifier: Modifier,
                       historyviewModel: HistoryViewModel,
                       activity: List<Activity>,
                       navController: NavController
) {

        Text(text = "Verlauf", fontSize = 40.sp)
        LazyColumn(modifier = modifier) {
            items(activity) { activity ->
                ActivityColumn(
                    activity = activity,
                    navController = navController,
                    historyviewModel = historyviewModel)
            }
    }
}

@Composable
fun ActivityColumn(
    modifier: Modifier = Modifier,
    historyviewModel: HistoryViewModel,
    navController: NavController,
    activity: Activity
) {
    Column() {
        Text(text = "Aktivität")
        Text(text = "Punkte: " + activity.points.toString(), fontSize = 18.sp)
        Text(text = "Dauer: " + formatTime(activity.length, showMilliSecs = false), fontSize = 18.sp)
        Spacer(modifier = Modifier.height(12.dp))
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ActivityPageContent(modifier: Modifier, navController: NavController, activityViewModel: ActivityViewModel) {
    val screen = BottomBarScreen.MainPage

    val time by activityViewModel.elapsedTime.collectAsState()
    val point by activityViewModel.currentPoints.collectAsState()

    var userActivityId: Int by remember { mutableStateOf(0) }

    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = formatTime(time, showMilliSecs = false))
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = point.toString())
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Button(onClick = {
                if (!locationPermissions.allPermissionsGranted || locationPermissions.shouldShowRationale) {
                    locationPermissions.launchMultiplePermissionRequest()
                } else {
                    activityViewModel.toggleStopWatch()
                }}) {
                Text(if (activityViewModel.isRunning) "Pause" else "Start")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                if (point == 0L) {
                    activityViewModel.reset()
                    navController.navigate(screen.route)
                } else {
                    activityViewModel.reset()
                    navController.navigate(screen.route)
                    var editActivity = Activity(
                        userActivityId = userActivityId,
                        points = point,
                        length = time
                    )
                    activityViewModel.addNewActivity(editActivity)
                }
            }) {
                Text("Beenden")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
    }


}


@Composable
fun StartActivity(modifier: Modifier) {
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(5.dp)
        .clickable {

        },
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(10.dp)) {

    }
}

fun formatTime(timeInMillis: Long, showMilliSecs: Boolean): String {
    val hours = (timeInMillis / 3600000)
    val minutes = (timeInMillis % 3600000) / 60000
    val seconds = (timeInMillis % 60000) / 1000
    val milliseconds = (timeInMillis % 1000) / 10

    if (showMilliSecs) {
        return String.format(Locale.getDefault(), "%02d:%02d:%02d:%02d", hours, minutes, seconds, milliseconds)
    } else {
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
    }
}



