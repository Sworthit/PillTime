package com.example.pilltime.compose.pillDayPicker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Day (
    val name: String,
    initialSelected: Boolean = false
) {
    var checked by mutableStateOf(initialSelected)
}


