package com.example.pilltime.data.mapper

import com.example.pilltime.data.entity.PillEntity
import com.example.pilltime.domain.Pill
import java.util.Date

fun PillEntity.toPill(): Pill? {
    return dateFromTimestamp(endDate)?.let {
        Pill(
            id = id,
            name = name,
            dailyDosage = dailyDosage,
            daysOfTheWeek = daysOfTheWeek,
            endDate = it,
            dailyDosageTimes = dailyDosageTimes,
        )
    }
}
fun PillEntity.toPill(newId: Long): Pill? {
    return dateFromTimestamp(endDate)?.let {
        Pill(
            id = newId,
            name = name,
            dailyDosage = dailyDosage,
            daysOfTheWeek = daysOfTheWeek,
            endDate = it,
            dailyDosageTimes = dailyDosageTimes,
        )
    }
}

fun Pill.toPillEntity(): PillEntity? {
    return dateToTimestamp(endDate)?.let {
        PillEntity(
            id = id!!,
            name = name,
            dailyDosageTimes = dailyDosageTimes,
            endDate = it,
            dailyDosage = dailyDosage,
            daysOfTheWeek = daysOfTheWeek
        )
    }
}

fun dateFromTimestamp(value: Long?): Date? {
    return value?.let { Date(it) }
}

fun dateToTimestamp(date: Date?): Long? {
    return date?.time
}