package com.example.sportapp.widgets

import android.R.attr.padding
import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sportapp.models.ActivityRepository.getActivities
import com.example.sportapp.viewmodels.HistoryViewModel
import com.example.sportapp.storage.Activity
import com.example.sportapp.storage.User
import com.example.sportapp.viewmodels.ActivityViewModel
import com.example.sportapp.viewmodels.MainViewModel

@Composable
fun MainPageContent(modifier: Modifier, navController: NavController, mainViewModel: MainViewModel, user: List<User>) {
    val screen = OtherScreens.ActivityPage
    val allPoints = user.sumOf { it.totalPoints }
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
    }
    Spacer(modifier = Modifier)
    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Gesampunkte", fontSize = 20.sp)
        Text(text = allPoints.toString(), fontSize = 30.sp)
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
                ActivityColumn(activity = activity,
                    navController = navController,
                    historyviewModel = historyviewModel)
            }
    }
}

@Composable
fun ActivityPageContent(modifier: Modifier, navController: NavController, activityViewModel: ActivityViewModel) {
    val screen = BottomBarScreen.MainPage

    var title by remember { mutableStateOf("") }
    var userActivityId: Long by remember { mutableStateOf(0) }
    var points by remember { mutableStateOf(0) }
    var length by remember { mutableStateOf(0) }
    var errorMessageTitle by remember { mutableStateOf<String?>(null) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            value = title,
            onValueChange = { newTitle ->
                title = newTitle
                errorMessageTitle = if (newTitle.isNotEmpty()) {
                    null
                } else
                    "Invalid Title"
            },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessageTitle != null
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            value =  if (points == 0) "" else points.toString(),
            onValueChange = { points = it.toInt() },
            label = { Text(text = "points") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            value =  if (length == 0) "" else length.toString(),
            onValueChange = { length = it.toInt() },
            label = { Text(text = "length in sec") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                if (title.isNotEmpty()) {
                    var editActivity = Activity(
                        userActivityId = userActivityId,
                        title = title,
                        points = points,
                        length = length
                    )
                    activityViewModel.addNewActivity(editActivity)
                    navController.navigate(screen.route)

                    userActivityId = System.currentTimeMillis()
                    title = ""
                    points = 0
                    length = 0

                }


            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Aktivität hinzufügen")
        }
    }


}
@Composable
fun ActivityColumn(modifier: Modifier = Modifier,
                   historyviewModel: HistoryViewModel,
                   navController: NavController,
                   activity: Activity) {
    Column() {
        Text(text = "Title: " + activity.title, fontSize = 24.sp)
        Text(text = "Punkte: " + activity.points.toString(), fontSize = 18.sp)
        Text(text = "Dauer: " + historyviewModel.secondsToFormattedTime(activity.length), fontSize = 18.sp)
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