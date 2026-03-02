package com.example.sportapp.storage


import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface dao {
    /*Activity Table*/
    @Insert
    fun createActivity (activity:Activity)

    @Delete
    fun deleteActivity (activity: Activity)

    @Query("Select * from activities ORDER BY userActivityId DESC")
    fun getAllActivities (): Flow<List<Activity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun initiatePoints (totalPoints: TotalPoints)


    @Query("UPDATE totalPoints SET totalPoints = totalPoints + :newPoints WHERE databaseId = 1")
    fun addToTotalPoints(newPoints: Long)

    @Query("SELECT * FROM TOTALPOINTS WHERE databaseId = 1")
    fun getAllPoints(): Flow<TotalPoints?>

    @Query("UPDATE totalPoints set totalPoints = :resetPointsValue where databaseId = 1")
    fun resetPoints (resetPointsValue: Long)

    /*User Data Table*/
//    @Insert
//    fun createUser (user: User)
//
//    @Delete
//    fun deleteUser (user:User)
//
//    @Update
//    fun addPoints (user: User): List<UserWithActivities>
//
//    @Transaction
//    @Query("Select totalPoints from user")
//    fun getTotalPoints (): Flow<List<User>>



}