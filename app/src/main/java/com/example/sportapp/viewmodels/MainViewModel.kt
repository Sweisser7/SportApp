package com.example.sportapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapp.models.Activity
import com.example.sportapp.storage.User
import com.example.sportapp.storage.repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MainViewModel(val repository: repository) : ViewModel(), BasicViewModel {

    private val mutableTotalPoints = MutableStateFlow(listOf<User>())

    val allUser: StateFlow<List<User>> = mutableTotalPoints.asStateFlow()

    fun updateTotalPoints (user: User) {
        repository.returnAddPoints(user)
    }

    init {
        viewModelScope.launch { repository.returnGetTotalPoints().distinctUntilChanged().collect {
                points -> mutableTotalPoints.value = points
        } }
    }

}