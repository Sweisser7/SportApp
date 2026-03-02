package com.example.sportapp.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sportapp.viewmodels.HistoryViewModel
import com.example.sportapp.storage.Activity
import com.example.sportapp.viewmodels.ActivityViewModel
import com.example.sportapp.viewmodels.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.flow.lastOrNull
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
    }

    Spacer(modifier = Modifier.height(250.dp))
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        FilledTonalButton(onClick = {navController.navigate(screen.route) }, modifier.fillMaxSize().padding(20.dp)) {
            Text(text = "Aktivität starten", fontSize = 35.sp)
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
fun HistoryPageContent(
    modifier: Modifier,
    historyviewModel: HistoryViewModel,
    activity: List<Activity>,
    navController: NavController
) {
    Column(modifier = modifier) { // Column um den Header und die Liste zu gruppieren

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            // Hier nutzen wir itemsIndexed statt items
            items(activity) { singleActivity ->
                ActivityColumn(
                    activity = singleActivity,
                    navController = navController,
                    historyviewModel = historyviewModel
                )
            }
        }
    }
}

@Composable
fun ActivityColumn(
    modifier: Modifier = Modifier,
    historyviewModel: HistoryViewModel,
    navController: NavController,
    activity: Activity,
) {

    Column(modifier = modifier.padding(8.dp)) {

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Column() {
                Text(text = "Aktivität ${activity.userActivityId}", fontWeight = FontWeight.SemiBold)
                Text(text = "Punkte: ${activity.points}", fontSize = 18.sp)
                Text(
                    text = "Dauer: ${formatTime(activity.length, showMilliSecs = false)}",
                    fontSize = 18.sp
                )
            }
            Icon(modifier = Modifier
                .clickable { historyviewModel.deleteActivity(activity) }
                .size(30.dp),
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Activity")

        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 8.dp),
            thickness = DividerDefaults.Thickness,
            color = DividerDefaults.color
        )
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ActivityPageContent(modifier: Modifier,
                        navController: NavController,
                        activityViewModel: ActivityViewModel,
                        activity: List<Activity>) {
    val screen = BottomBarScreen.MainPage
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

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
            .height(screenHeight / 2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = formatTime(time, showMilliSecs = false), fontSize = 30.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = point.toString(), fontSize = 30.sp)
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
                if (point > 0L) {
                    val nextId = activity.size + 1

                    val editActivity = Activity(
                        userActivityId = nextId,
                        points = point,
                        length = time
                    )

                    activityViewModel.addNewActivity(editActivity)
                    activityViewModel.reset()
                    navController.navigate(screen.route)
                } else {
                    activityViewModel.reset()
                    navController.navigate(screen.route)
                }
            }) {
                Text("Beenden")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
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



