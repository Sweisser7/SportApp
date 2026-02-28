package com.example.sportapp.viewmodels

import android.R.attr.start
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapp.models.Coordinate
import com.example.sportapp.models.MatchingApiClient
import com.example.sportapp.models.MatchingState
import com.example.sportapp.storage.Activity
import com.example.sportapp.storage.repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class ActivityViewModel(val repository: repository) : ViewModel(), BasicViewModel {

    private val _matchingState = MutableStateFlow<MatchingState>(MatchingState.Idle)
    private val _elapsedTime = MutableStateFlow(0L)
    private val _currentPoints = MutableStateFlow(0L)
    val matchingState: StateFlow<MatchingState> = _matchingState.asStateFlow()
    val elapsedTime = _elapsedTime.asStateFlow()
    val currentPoints = _currentPoints.asStateFlow()
    private var timerJob: Job? = null
    private var pointsJob: Job? = null
    private var idJob: Job? = null

    var isRunning = false

    fun toggleStopWatch() {
        if (isRunning) {
            stop()
        } else {
            start()

        }
    }

    private fun start() {
        isRunning = true // Status setzen
        timerJob = viewModelScope.launch {
            val startTime = System.currentTimeMillis() - _elapsedTime.value
            while (isRunning) {
                _elapsedTime.value = System.currentTimeMillis() - startTime

                delay(10)
            }
        }
        pointsJob = viewModelScope.launch {
            val startPoints = System.currentTimeMillis() - _currentPoints.value
            while (isRunning) {
                _currentPoints.value = (System.currentTimeMillis() - startPoints) /100
                delay(1000)
            }
        }
    }

    private fun stop() {
        isRunning = false // Status setzen
        timerJob?.cancel()
        pointsJob?.cancel()
    }

    fun reset() {
        stop()
        _elapsedTime.value = 0L
        _currentPoints.value = 0L
    }





//    fun fetchMatchedRoute(rawPath: List<Coordinate>) {
//        viewModelScope.launch {
//            _matchingState.value = MatchingState.Loading
//
//            val success = apiClient.fetchMatching(rawPath)
//
//            if (success) {
//                _matchingState.value = MatchingState.Success(apiClient.matchedCoordinates)
//            } else {
//                _matchingState.value = MatchingState.Error("Fehler beim Abrufen der API")
//            }
//        }
//    }

    fun addNewActivity (activity: Activity) {
        repository.returnInsert(activity)
    }



}