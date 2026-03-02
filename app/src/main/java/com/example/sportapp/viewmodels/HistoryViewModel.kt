package com.example.sportapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapp.storage.Activity
import com.example.sportapp.storage.TotalPoints
import com.example.sportapp.storage.repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(val repository: repository) : ViewModel(), BasicViewModel{

    private val mutableAllActivities = MutableStateFlow(listOf<Activity>())

    val allActivities: StateFlow<List<Activity>> = mutableAllActivities.asStateFlow()

    val totalPoints: StateFlow<TotalPoints?> = repository.returnAllPoints()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Hält den Flow 5 Sek. nach UI-Stopp aktiv
            initialValue = null // Startwert, bis die DB geantwortet hat
        )



    init {
        viewModelScope.launch { repository.returnAllActivities().distinctUntilChanged().collect {
                activities -> mutableAllActivities.value = activities
        }
            repository.ensurePointsExist()
        }
    }
}