package com.example.sportapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.sportapp.storage.repository

class AchievementViewModel (repository: repository): ViewModel(), BasicViewModel  {

    val totalPoints = repository.returnAllPoints().sumOf { it }
}