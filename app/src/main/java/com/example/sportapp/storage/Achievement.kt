package com.example.sportapp.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "achievements")
data class Achievement(
    @PrimaryKey val id: String,          // z.B. "total_act_10" oder "total_dist_100"
    val title: String,                   // "10 Aktivitäten"
    val type: String,                    // "ACTIVITIES", "DISTANCE", "TIME"
    val requiredValue: Long,             // z.B. 10 (Aktivitäten) oder 100000 (Meter)
    val isUnlocked: Boolean = false
)