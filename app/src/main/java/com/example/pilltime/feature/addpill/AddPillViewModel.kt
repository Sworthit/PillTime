package com.example.pilltime.feature.addpill

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pilltime.PillNotificationService
import com.example.pilltime.compose.pillDayPicker.Day
import com.example.pilltime.data.entity.PillEntity
import com.example.pilltime.data.mapper.toPill
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class AddPillViewModel @Inject constructor(private val addPillUseCase: AddPillUseCase) : ViewModel() {
    var daysOfWeek = mutableListOf(
        Day("monday"),
        Day("tuesday"),
        Day("wednesday"),
        Day("thursday"),
        Day("friday"),
        Day("saturday"),
        Day("sunday"),
    )

    var pillsDosageTimes = mutableListOf<String>()

    fun updateSelectedDaysOfWeek(day: Day) {
        val index = daysOfWeek.indexOf(day)
        daysOfWeek = daysOfWeek.toMutableList().also {
            it[index].checked = !day.checked
        }
    }

    fun getSelectedDaysOfWeek(): List<String> {
        var selectedDays = mutableListOf<String>()

        daysOfWeek.forEach { day ->
            if (day.checked) {
                selectedDays.add(day.name)
            }
        }

        return selectedDays
    }

    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(Date(millis))
    }

    fun addPill(
        context: Context,
        name: String,
        dailyDosage: Int,
        dailyDosageTimes: List<String>,
        daysOfTheWeek: List<String>,
        endDate: Long,
    ) {
        val newPill = PillEntity(
            id = 0,
            name = name,
            dailyDosage = dailyDosage,
            dailyDosageTimes = dailyDosageTimes as ArrayList<String>,
            daysOfTheWeek = daysOfTheWeek as ArrayList<String>,
            endDate = endDate
        )
        viewModelScope.launch {
            val returnId = addPillUseCase.addPill(newPill)
            val service = PillNotificationService(context)
            newPill.toPill(returnId)?.let { service.schedulePillNotification(it) }
        }
    }


}
