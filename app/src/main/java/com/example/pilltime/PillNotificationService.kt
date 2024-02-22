package com.example.pilltime

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.pilltime.domain.Pill
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import kotlin.math.log

class PillNotificationService(
    private val context: Context
) {
    fun schedulePillNotification(pill: Pill) {

        val alarmService = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val endDateInMillis = pill.endDate.time
        val weeks = calculateWeeks(endDateInMillis)
        val intent = Intent(context, PillNotificationReceiver::class.java)
        var initialAlarmsCount = weeks * pill.daysOfTheWeek.size * pill.dailyDosageTimes.size
        intent.putExtra(PILL_INTENT, pill)
        for (i in 1 .. weeks) {
            pill.daysOfTheWeek.forEachIndexed{ di, day ->
                pill.dailyDosageTimes.forEachIndexed { ti, time ->
                    val dateCheck = Calendar.getInstance()
                    val dayEnum = getDayEnum(day)
                    val selectedTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))
                    val calendar: Calendar = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, selectedTime.hour)
                        set(Calendar.MINUTE, selectedTime.minute)
                        set(Calendar.DAY_OF_WEEK, dayEnum)
                        if (i != 1){
                            add(Calendar.WEEK_OF_YEAR, 1)
                        }
                    }
                    if (!calendar.before(dateCheck)) {
                        intent.putExtra(PILL_HASHCODE, pill.hashCode() + initialAlarmsCount)
                        val pendingIntent = PendingIntent.getBroadcast(
                            context,
                            pill.hashCode() + initialAlarmsCount,
                            intent,
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
                        )
                        if (pendingIntent != null) {
                            alarmService.setExactAndAllowWhileIdle(
                                AlarmManager.RTC_WAKEUP,
                                calendar.timeInMillis,
                                pendingIntent
                            )
                            initialAlarmsCount--
                        }
                    }
                }
            }
        }
    }

    fun updatePillNotification(pill: Pill) {
        val alarmService = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val endDateInMillis = pill.endDate.time
        val weeks = calculateWeeks(endDateInMillis)
        val intent = Intent(context, PillNotificationReceiver::class.java)
        var initialAlarmsCount = weeks * pill.daysOfTheWeek.size * pill.dailyDosageTimes.size
        intent.putExtra(PILL_INTENT, pill)
        for (i in 1 .. weeks) {
            pill.daysOfTheWeek.forEachIndexed{ di, day ->
                pill.dailyDosageTimes.forEachIndexed { ti, time ->
                    val dateCheck = Calendar.getInstance()
                    val dayEnum = getDayEnum(day)
                    val selectedTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"))
                    val calendar: Calendar = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, selectedTime.hour)
                        set(Calendar.MINUTE, selectedTime.minute)
                        set(Calendar.DAY_OF_WEEK, dayEnum)
                        if (i != 1){
                            add(Calendar.WEEK_OF_YEAR, 1)
                        }
                    }
                    if (!calendar.before(dateCheck)) {
                        intent.putExtra(PILL_HASHCODE, pill.hashCode() + initialAlarmsCount)
                        val pendingIntent = PendingIntent.getBroadcast(
                            context,
                            pill.hashCode() + initialAlarmsCount,
                            intent,
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
                        )
                        if (pendingIntent != null) {
                            alarmService.setExactAndAllowWhileIdle(
                                AlarmManager.RTC_WAKEUP,
                                calendar.timeInMillis,
                                pendingIntent
                            )
                            initialAlarmsCount--
                        }
                    }
                }
            }
        }
    }

    private fun calculateWeeks(endDate: Long): Int {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = endDate
        }
        return if (calendar.get(Calendar.WEEK_OF_YEAR) == Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)) 1
        else
            calendar.get(Calendar.WEEK_OF_YEAR) - Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) + 1
    }

    private fun getDayEnum(day: String): Int {
        return when (day) {
            "sunday" -> 1
            "monday" -> 2
            "tuesday" -> 3
            "wednesday" -> 4
            "thursday" -> 5
            "friday" -> 6
            "saturday" -> 7
            else -> 0
        }
    }

    companion object {
        const val PILL_CHANNEL_ID = "pill_channel"
    }
}