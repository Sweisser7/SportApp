package com.example.sportapp.storage


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface dao {
    @Insert
    fun Insert (activity:Activity)

    @Delete
    fun Delete (activity: Activity)

    @Query("Select * from activities")
    fun getAllActivities (): Flow<List<Activity>>


}