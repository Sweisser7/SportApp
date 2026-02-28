package com.example.sportapp.models

data class Coordinate(val longitude: Double, val latitude: Double)

sealed class MatchingState {
    object Idle : MatchingState()
    object Loading : MatchingState()
    data class Success(val coordinates: List<Coordinate>) : MatchingState()
    data class Error(val message: String) : MatchingState()
}
