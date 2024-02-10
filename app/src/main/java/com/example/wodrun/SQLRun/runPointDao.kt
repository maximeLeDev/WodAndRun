package com.example.myapplication.SQL

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.wodrun.SQLRun.runPoint

@Dao
interface runPointDao {
    @Query("SELECT * FROM runPoint")
    suspend fun getAllRun(): List<runPoint>

    @Insert
    suspend fun insertAll(vararg runPoints: runPoint)

    @Delete
    suspend fun delete(runPoint: runPoint)
}