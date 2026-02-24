package com.example.sportapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.sportapp.storage.Activity
import com.example.sportapp.storage.repository

class ActivityViewModel(val repository: repository) : ViewModel(), BasicViewModel {

    fun addNewActivity (activity: Activity) {
        repository.returnInsert(activity)
    }


}