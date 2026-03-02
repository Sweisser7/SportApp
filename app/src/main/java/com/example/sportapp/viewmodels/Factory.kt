package com.example.sportapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sportapp.location.LocationManager
import com.example.sportapp.storage.repository

class Factory(
    private val repository: repository,
    private val locationManager: LocationManager
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HistoryViewModel::class.java) ->
                HistoryViewModel(repository = repository) as T
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(repository = repository) as T
            modelClass.isAssignableFrom(ActivityViewModel::class.java) ->
                ActivityViewModel(repository = repository, locationManager = locationManager) as T
            modelClass.isAssignableFrom(AchievementViewModel::class.java) ->
                AchievementViewModel(repository = repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}