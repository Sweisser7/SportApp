package com.example.sportapp.models



data class Activity(
    val userActivityId: String,
    val title: String,
    val points: Int,
    val length: Int
)

object ActivityRepository {
    private val activities: MutableList<Activity> = mutableListOf(
        Activity(
            userActivityId = "",
            title = "Aktivität 1",
            points = 1244,
            length = 300
        )
    )

    fun getActivities(): List<Activity> {
        return listOf(*activities.toTypedArray())
    }

    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    fun deleteActivityById(id: String): Boolean {
        return activities.removeIf { it.userActivityId == id }
    }

}

