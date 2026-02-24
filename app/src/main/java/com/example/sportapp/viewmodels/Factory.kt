package com.example.sportapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sportapp.storage.repository

class Factory (private val repository: repository):ViewModelProvider.Factory {
    @Override
    override fun <T: ViewModel> create(model: Class<T>):T=when (model) {
        HistoryViewModel::class.java -> HistoryViewModel(repository = repository)
        MainViewModel::class.java -> MainViewModel(repository = repository)
        ActivityViewModel::class.java -> ActivityViewModel(repository = repository)
        else -> throw IllegalArgumentException("Oh oh!")
    } as T
}