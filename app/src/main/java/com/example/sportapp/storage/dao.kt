package com.example.sportapp.storage


import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface dao {
    /*Activity Table*/
    @Insert
    fun createActivity (activity:Activity)

    @Delete
    fun deleteActivity (activity: Activity)

    @Query("Select * from activities")
    fun getAllActivities (): Flow<List<Activity>>

    /*User Data Table*/
    @Insert
    fun createUser (user: User)

    @Delete
    fun deleteUser (user:User)

    @Update
    fun addPoints (user: User): List<UserWithActivities>

    @Transaction
    @Query("Select totalPoints from user")
    fun getTotalPoints (): Flow<List<User>>



}