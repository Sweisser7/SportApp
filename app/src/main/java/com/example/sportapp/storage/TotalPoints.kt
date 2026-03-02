package com.example.sportapp.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "totalPoints")
data class TotalPoints (
    @PrimaryKey(autoGenerate = true)
    val databaseId: Long=1,
    var totalPoints: Long
)