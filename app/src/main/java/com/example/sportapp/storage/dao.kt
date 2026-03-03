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

    @Query("Select * from activities ORDER BY userActivityId DESC")
    fun getAllActivities (): Flow<List<Activity>>

    @Query("SELECT SUM(length) FROM activities")
    fun getTotalDuration(): Long?

    @Query("SELECT COUNT(*) FROM activities")
    fun getTotalActivitiesCount(): Long

    /*TotalPoints Table*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun initiatePoints (totalPoints: TotalPoints)

    @Query("UPDATE totalPoints SET totalPoints = totalPoints + :newPoints WHERE databaseId = 1")
    fun addToTotalPoints(newPoints: Long)

    @Query("SELECT * FROM TOTALPOINTS WHERE databaseId = 1")
    fun getAllPoints(): Flow<TotalPoints?>

    @Query("UPDATE totalPoints set totalPoints = totalPoints - :resetPointsValue where databaseId = 1")
    fun subtractPoints (resetPointsValue: Long)

    @Query("SELECT totalPoints FROM totalPoints")
    fun getTotalPoints(): Long?

    /*Achievements Table*/
    @Query("SELECT * FROM achievements where isUnlocked = 1")
    fun getUnlockedAchievements(): Flow<List<Achievement>>

    @Query("SELECT * FROM achievements WHERE isUnlocked = 0 AND type = :type")
    fun getLockedAchievementsByType(type: String): List<Achievement>

    @Update
    fun updateAchievement(achievement: Achievement)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertInitialAchievements(achievements: List<Achievement>)
}