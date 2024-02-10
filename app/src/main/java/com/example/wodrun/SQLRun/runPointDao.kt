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

    @Query("SELECT * FROM runPoint ORDER BY idRunPoint DESC LIMIT 1")
    suspend fun getLastMark(): runPoint?

    @Query("SELECT * FROM runPoint WHERE subDescriptionRunPoint = :subDescriptionRunPoint")
    suspend fun getMarkBySubDescription(subDescriptionRunPoint: String): runPoint?

    @Insert
    suspend fun insertAll(vararg runPoints: runPoint)

    @Delete
    suspend fun delete(runPoint: runPoint)
}