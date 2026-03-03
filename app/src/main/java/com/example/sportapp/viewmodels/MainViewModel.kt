package com.example.sportapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapp.storage.TotalPoints
import com.example.sportapp.storage.repository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(val repository: repository) : ViewModel(), BasicViewModel {

    val totalPoints: StateFlow<TotalPoints?> = repository.returnAllPoints()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    init {
        viewModelScope.launch {
            repository.ensurePointsExist()
            repository.initAchievements()
        }
    }
}