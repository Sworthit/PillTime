package com.example.pilltime.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Pill(
    val id: Long?,
    val name: String,
    val dailyDosage: Int,
    val dailyDosageTimes: ArrayList<String>,
    val daysOfTheWeek: ArrayList<String>,
    val endDate: Date,
) : Parcelable