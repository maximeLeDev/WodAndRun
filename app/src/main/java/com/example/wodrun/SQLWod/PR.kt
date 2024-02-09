package com.example.wodrun.SQLWod

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PR(
    @PrimaryKey(autoGenerate = true) val idPR: Int,
    @ColumnInfo(name = "idExoPR") val idExo: String,
    @ColumnInfo(name = "valuePR") val valuePR: String
)