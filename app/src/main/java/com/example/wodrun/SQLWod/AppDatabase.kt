package com.example.wodrun.SQL

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.SQL.PRDao
import com.example.wodrun.SQLWod.PR

@Database(entities = [PR::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun prDao(): PRDao
}