package com.example.wodrun.SQLRun

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class runPoint(
    @PrimaryKey(autoGenerate = true) val idRunPoint: Int,
    @ColumnInfo(name = "longitudeRunPoint") val longitudeRunPoint: Double,
    @ColumnInfo(name = "latitudeRunPoint") val latitudeRunPoint: Double,
    @ColumnInfo(name = "titleRunPoint") val titleRunPoint: String,
    @ColumnInfo(name = "descriptionRunPoint") val descriptionRunPoint: String
)
