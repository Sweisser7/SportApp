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

    fun returnInsert (activity: Activity) = dao.createActivity(activity)

    fun returnDelete (activity: Activity) = dao.deleteActivity(activity)

    fun returnAllActivities (): Flow<List<Activity>> = dao.getAllActivities()

    fun returnAllPoints (): List<Int> = dao.getAllPoints()

//    fun returnCreateUser (user: User) = dao.createUser(user)
//
//    fun returnDeleteUser (user: User) = dao.deleteUser(user)
//
//    fun returnAddPoints (user: User): List<UserWithActivities> = dao.addPoints(user)
//
//    fun returnGetTotalPoints (): Flow<List<User>> = dao.getTotalPoints()
}