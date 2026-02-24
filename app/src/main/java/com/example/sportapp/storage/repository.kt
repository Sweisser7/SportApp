package com.example.sportapp.storage


import kotlinx.coroutines.flow.Flow

class repository (private val dao: dao) {

    companion object {
        @Volatile
        private var instance:repository?=null

        fun returnInstance (dao:dao) = instance ?: synchronized(this) {
            instance ?: repository(dao).also { instance=it }
        }

    }

    fun returnInsert (activity: Activity) = dao.Insert(activity)

    fun returnDelete (activity: Activity) = dao.Delete(activity)

    fun returnAllActivities (): Flow<List<Activity>> = dao.getAllActivities()
}