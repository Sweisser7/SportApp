package com.example.sportapp.models

data class User(
    var userId: Long,
    var totalPoints: Int
)

object userRepository {

    private val users: MutableList<User>  = mutableListOf(
        User(
            userId = 0,
            totalPoints = 0
        )
    )

    fun getUser(): List<User> {
        return listOf(*users.toTypedArray())
    }

}

