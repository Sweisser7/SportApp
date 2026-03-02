package com.example.sportapp.storage


import kotlinx.coroutines.flow.Flow

class repository (private val dao: dao) {

    companion object {
        @Volatile
        private var instance: repository? = null

        fun returnInstance(dao: dao) = instance ?: synchronized(this) {
            instance ?: repository(dao).also { instance = it }
        }

    }

    fun returnInsertActivity(activity: Activity) = dao.createActivity(activity)

    fun returnDeleteActivity(activity: Activity) = dao.deleteActivity(activity)

    fun returnAllActivities(): Flow<List<Activity>> = dao.getAllActivities()

    fun returnAllPoints(): Flow<TotalPoints?> = dao.getAllPoints()

    fun ensurePointsExist() {
        dao.initiatePoints(TotalPoints(databaseId = 1, totalPoints = 0))
    }

    fun addPoints(amount: Long) {
        dao.addToTotalPoints(amount)
    }

}