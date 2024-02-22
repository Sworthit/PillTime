package com.example.pilltime.compose.pillcard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pilltime.data.entity.PillEntity
import com.example.pilltime.ui.theme.Pink3

@Composable
fun PillCard(pillEntity: PillEntity, selectedPill: (Long) -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Pink3
        ),
        shape = RoundedCornerShape(corner = CornerSize(10.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .clickable { selectedPill(pillEntity.id) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Text(text = pillEntity.name, style = MaterialTheme.typography.headlineLarge)
                Spacer(modifier = Modifier.height(5.dp))
                pillEntity.dailyDosageTimes.forEach {
                    Text(text = "At $it")
                }
            }
        }

    }
}