package com.example.myapplication.SQL

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.wodrun.SQLWod.PR

@Dao
interface PRDao {
    @Query("SELECT * FROM pr")
    suspend fun getAll(): List<PR>

    @Query("SELECT * FROM pr WHERE idPR IN (:prIds)")
    suspend fun loadAllByIds(prIds: IntArray): List<PR>

    @Query("SELECT * FROM pr WHERE idExoPR = :ExoPR")
    suspend fun findByIdExoPR(ExoPR: String): List<PR>

    @Query("SELECT * FROM pr WHERE idExoPR = :ExoPR ORDER BY idPR DESC LIMIT 1")
    suspend fun getLastPersonalRecordForExo(ExoPR: String): PR?
    @Insert
    suspend fun insertAll(vararg prs: PR)

    @Delete
    suspend fun delete(pr: PR)
}