package com.example.wodrun

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.SQL.PRDao
import com.example.myapplication.SQL.runPointDao
import com.example.wodrun.SQLRun.runPoint
import com.example.wodrun.SQLWod.PR

@Database(entities = [PR::class, runPoint::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun prDao(): PRDao
    abstract fun runPointDao(): runPointDao
}