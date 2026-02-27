package com.example.sportapp.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class Activity (
    @PrimaryKey(autoGenerate = true)
    val databaseId: Long=0,
    var userActivityId: String,
    var title: String,
    var points: Int,
    var length: Int
)