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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun initiatePoints (totalPoints: TotalPoints)


    @Query("UPDATE totalPoints SET totalPoints = totalPoints + :newPoints WHERE databaseId = 1")
    fun addToTotalPoints(newPoints: Long)

    @Query("SELECT * FROM TOTALPOINTS WHERE databaseId = 1")
    fun getAllPoints(): Flow<TotalPoints?>

    @Update
    fun resetPoints (totalPoints: TotalPoints)

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