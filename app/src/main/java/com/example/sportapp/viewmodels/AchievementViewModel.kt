package com.example.sportapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapp.storage.Achievement
import com.example.sportapp.storage.TotalPoints
import com.example.sportapp.storage.repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AchievementViewModel (repository: repository): ViewModel(), BasicViewModel  {

    private val mutableUnlockedAchievements = MutableStateFlow(listOf<Achievement>())
    val unlockedAchievements: StateFlow<List<Achievement>> = mutableUnlockedAchievements.asStateFlow()

    val totalPoints: StateFlow<TotalPoints?> = repository.returnAllPoints()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    init {
        repository.checkTotalMilestones()
        viewModelScope.launch {
            repository.returnUnlockedAchievements().distinctUntilChanged().collect {
                    achievements -> mutableUnlockedAchievements.value = achievements
            }
            repository.ensurePointsExist()

        }
    }
}