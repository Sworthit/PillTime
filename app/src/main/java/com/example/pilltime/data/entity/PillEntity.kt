package com.example.pilltime.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pill_table")
data class PillEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val dailyDosage: Int,
    val dailyDosageTimes: ArrayList<String>,
    val daysOfTheWeek: ArrayList<String>,
    val endDate: Long,
)