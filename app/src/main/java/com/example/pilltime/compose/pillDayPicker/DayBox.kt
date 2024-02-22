package com.example.pilltime.compose.pillDayPicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DayBox(
    modifier: Modifier = Modifier,
    data: Day,
    selected: Boolean,
    onClickListener: (Boolean) -> Unit,
) {
    val dayName = data.name.substring(0, 3).uppercase()
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .clickable { onClickListener(selected) },
        colors = CardDefaults.cardColors(
            containerColor = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.secondary
            }
        ),
    ) {
        Column(
            modifier = Modifier
                .width(40.dp)
                .height(48.dp)
                .padding(4.dp)
        ){
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = dayName,
                style = MaterialTheme.typography.bodySmall,
            )
            if (data.checked) {
                Icon(Icons.Filled.Done, contentDescription = "Selected")
            }
        }
    }
}
