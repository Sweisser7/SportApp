package com.example.sportapp.models

import android.os.Bundle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


data class Activity(
    val userActivityId: Int,
    val points: Long,
    val length: Long
)

object ActivityRepository {
    private val activities: MutableList<Activity> = mutableListOf(
        Activity(
            userActivityId = 0,
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

    fun deleteActivityById(id: Int): Boolean {
        return activities.removeIf { it.userActivityId == id }
    }


}

