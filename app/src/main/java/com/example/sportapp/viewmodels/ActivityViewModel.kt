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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch







class ActivityViewModel(
    private val repository: repository,
    private val locationManager: LocationManager
) : ViewModel(), BasicViewModel {

    private val _elapsedTime = MutableStateFlow(0L)
    val elapsedTime = _elapsedTime.asStateFlow()

    private val _currentPoints = MutableStateFlow(0L)
    val currentPoints = _currentPoints.asStateFlow()

    private val _currentLocation = MutableStateFlow<Location?>(null)
    val currentLocation = _currentLocation.asStateFlow()

    private var timerJob: Job? = null
    private var locationJob: Job? = null

    // Hilfsvariable, um die vorherige Position zu speichern
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

        // Timer Job (Zeitmessung)
        timerJob = viewModelScope.launch {
            val startTime = System.currentTimeMillis() - _elapsedTime.value
            while (isRunning) {
                _elapsedTime.value = System.currentTimeMillis() - startTime
                delay(10)
            }
        }

        // Location & Points Job kombiniert
        // Wir berechnen die Punkte direkt basierend auf der Bewegung
        locationJob = viewModelScope.launch {
            locationManager.getLocationUpdates().collect { newLocation ->
                if (isRunning) {
                    lastLocation?.let { previous ->
                        val distance = previous.distanceTo(newLocation)
                        if (distance > 1.0) { // Kleiner Schwellenwert gegen GPS-Zittern
                            _currentPoints.value += (distance.toLong()+10)
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
        lastLocation = null // Reset für den nächsten Start
    }

    fun reset() {
        stop()
        savePointsToDatabase()
        _elapsedTime.value = 0L
        _currentPoints.value = 0L
        _currentLocation.value = null
        lastLocation = null
    }

    fun addNewActivity(activity: Activity) {
        repository.returnInsertActivity(activity)
    }

    fun savePointsToDatabase() {
        viewModelScope.launch {
            val pointsToSave = _currentPoints.value
            repository.addPoints(pointsToSave)

            // Danach die aktuellen Punkte der Session zurücksetzen
        }
    }
}