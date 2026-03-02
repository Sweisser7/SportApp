package com.example.sportapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapp.storage.Activity
import com.example.sportapp.storage.repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.launch

class HistoryViewModel(val repository: repository) : ViewModel(), BasicViewModel{

    private val mutableAllActivities = MutableStateFlow(listOf<Activity>())
    private var idJob: Job? = null

    val totalPoints = repository.returnAllPoints().sumOf { it }

    val allActivities: StateFlow<List<Activity>> = mutableAllActivities.asStateFlow()

    fun deleteActivity (activity: Activity) {
        repository.returnDelete(activity)
    }
//    private val _activities = getActivities().toMutableStateList()
//    val activities: List<com.example.sportapp.models.Activity>
//        get() = _activities

    fun secondsToFormattedTime(totalSeconds: Long): String {
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }




    init {
        viewModelScope.launch { repository.returnAllActivities().distinctUntilChanged().collect {
                activities -> mutableAllActivities.value = activities
        } }
    }
}