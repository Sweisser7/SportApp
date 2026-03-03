package com.example.sportapp.viewmodels


import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapp.location.LocationManager
import com.example.sportapp.storage.Activity
import com.example.sportapp.storage.repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch



class ActivityViewModel(
    private val repository: repository,
    private val locationManager: LocationManager
) : ViewModel(), BasicViewModel {

    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime = _elapsedTime.asStateFlow()
    private val mutableAllActivities = MutableStateFlow(listOf<Activity>())
    val allActivities: StateFlow<List<Activity>> = mutableAllActivities.asStateFlow()
    private val _currentPoints = MutableStateFlow(0L)
    val currentPoints = _currentPoints.asStateFlow()
    private val _currentLocation = MutableStateFlow<Location?>(null)
    private var timerJob: Job? = null
    private var locationJob: Job? = null
    private var lastLocation: Location? = null
    var isRunning = false

    fun toggleStopWatch() {
        if (isRunning) {
            stop()
        } else {
            start()
        }
    }

    private fun start() {
        isRunning = true

        timerJob = viewModelScope.launch {
            val startTime = System.currentTimeMillis() - _elapsedTime.value
            while (isRunning) {
                _elapsedTime.value = System.currentTimeMillis() - startTime
                delay(10)
            }
        }

        locationJob = viewModelScope.launch {
            locationManager.getLocationUpdates().collect { newLocation ->
                if (isRunning) {
                    lastLocation?.let { previous ->
                        val distance = previous.distanceTo(newLocation)
                        if (distance < 500) {
                            _currentPoints.value += (distance.toLong()+10L)
                            delay(3000)
                        }
                    }
                    lastLocation = newLocation
                    _currentLocation.value = newLocation
                }
            }
        }
    }

    private fun stop() {
        isRunning = false
        timerJob?.cancel()
        locationJob?.cancel()
        lastLocation = null
    }

    fun reset() {
        stop()
        savePointsToDatabase()
        _elapsedTime.value = 0L
        _currentPoints.value = 0L
        _currentLocation.value = null
        lastLocation = null
    }

    fun saveActivity(points: Long, time: Long, currentListSize: Int) {
        viewModelScope.launch {
            val newActivity = Activity(
                userActivityId = currentListSize,
                points = points,
                length = time
            )
            repository.returnInsertActivity(newActivity)
            repository.checkTotalMilestones()
        }
    }

    fun savePointsToDatabase() {
        viewModelScope.launch {
            val pointsToSave = _currentPoints.value
            repository.addPoints(pointsToSave)
        }
    }

    init {
        viewModelScope.launch {
            repository.returnAllActivities().distinctUntilChanged().collect { activities ->
                mutableAllActivities.value = activities
            }
        }
    }
}