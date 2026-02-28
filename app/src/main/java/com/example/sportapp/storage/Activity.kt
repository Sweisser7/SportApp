package com.example.sportapp.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class Activity (
    @PrimaryKey(autoGenerate = true)
    val databaseId: Long=0,
    var userActivityId: Int,
    var points: Long,
    var length: Long
)