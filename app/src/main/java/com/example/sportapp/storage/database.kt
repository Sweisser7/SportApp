package com.example.sportapp.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Activity::class], version = 1, exportSchema = false)
abstract class database: RoomDatabase() {
 abstract fun dao():dao

 companion object {
  @Volatile
  private var instance: database? = null

  fun getDatabase(context: Context): database {
   return instance ?: synchronized(this) {
    Room.databaseBuilder(context, database::class.java, "activity_db")
     .fallbackToDestructiveMigration(true)
     .allowMainThreadQueries()
     .build()
     .also { instance = it }
   }
  }
 }
}