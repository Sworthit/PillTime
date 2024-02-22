package com.example.pilltime.compose.pillDayPicker

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DayBoxContent(
    modifier: Modifier = Modifier,
    data: List<Day>,
    onClickListener: (Day) -> Unit
){
    LazyRow {
        items(
            items = data,
            key = { day -> day.name }
        ) { day ->
            DayBox(data = day, selected = day.checked) { onClickListener(day) }
        }
    }
}