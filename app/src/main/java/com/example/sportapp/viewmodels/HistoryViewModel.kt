package com.example.sportapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapp.storage.Activity
import com.example.sportapp.storage.TotalPoints
import com.example.sportapp.storage.repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(val repository: repository) : ViewModel(), BasicViewModel{

    private val mutableAllActivities = MutableStateFlow(listOf<Activity>())
    private val _sortType = MutableStateFlow(SortType.NONE)
    val sortType = _sortType.asStateFlow()
    val allActivities: StateFlow<List<Activity>> = mutableAllActivities.asStateFlow()

    val totalPoints: StateFlow<TotalPoints?> = repository.returnAllPoints()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val activities: StateFlow<List<Activity>> = combine(allActivities, _sortType) { activityList, currentSort ->
        when (currentSort) {
            SortType.NONE -> activityList.sortedByDescending { it.userActivityId }
            SortType.POINTS -> activityList.sortedByDescending { it.points }
            SortType.TIME -> activityList.sortedByDescending { it.length }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun setSortType(type: SortType) {
        _sortType.value = type
    }

    fun deleteActivity (activity: Activity) {
            repository.returnDeleteActivity(activity)
        repository.returnSubtractPoints(activity.points)
    }

    init {
        repository.checkTotalMilestones()
        viewModelScope.launch { repository.returnAllActivities().distinctUntilChanged().collect {
                activities -> mutableAllActivities.value = activities
        }
            repository.ensurePointsExist()
        }
    }
}

enum class SortType {
    NONE,
    POINTS,
    TIME
}