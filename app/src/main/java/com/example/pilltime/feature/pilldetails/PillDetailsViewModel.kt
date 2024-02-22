package com.example.pilltime.feature.pilldetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.pilltime.PillNotificationService
import com.example.pilltime.compose.pillDayPicker.Day
import com.example.pilltime.data.entity.PillEntity
import com.example.pilltime.data.mapper.toPill
import com.example.pilltime.data.repository.PillRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject
import android.content.Context

@HiltViewModel
class PillDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PillRepository
): ViewModel() {
    private val pillId: Int = savedStateHandle.get<String>("pillId")!!.toInt()

    val pill = repository.getPill(pillId).asLiveData()

    var daysOfWeek = mutableListOf(
        Day("monday"),
        Day("tuesday"),
        Day("wednesday"),
        Day("thursday"),
        Day("friday"),
        Day("saturday"),
        Day("sunday"),
    )

    fun checkSelectedDays(days : ArrayList<String>) {
        days.forEach { selectedDay ->
            daysOfWeek.find { day -> day.name == selectedDay }?.checked = true
        }
    }

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

    fun updatePill(
        context: Context,
        name: String,
        dailyDosage: Int,
        dailyDosageTimes: List<String>,
        daysOfTheWeek: List<String>,
        endDate: Long,
    ) {
        val updatedPill = PillEntity(
            id = pill.value!!.id,
            name = name,
            dailyDosage = dailyDosage,
            dailyDosageTimes = dailyDosageTimes as ArrayList<String>,
            daysOfTheWeek = daysOfTheWeek as ArrayList<String>,
            endDate = endDate
        )

        viewModelScope.launch {
            repository.updatePill(updatedPill)
            val service = PillNotificationService(context)
            updatedPill.toPill()?.let { service.schedulePillNotification(it) }
        }
    }

    fun deletePill(
        pillEntity: PillEntity
    ) {
        viewModelScope.launch {
            repository.deletePill(pillEntity)
        }
    }


}