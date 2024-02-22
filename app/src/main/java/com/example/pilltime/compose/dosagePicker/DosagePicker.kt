package com.example.pilltime.compose.dosagePicker

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pilltime.R

@Composable
fun DosagePicker(
    modifier: Modifier = Modifier,
    height: Dp = 45.dp,
    min: Int = 1,
    max: Int = 10,
    default: Int,
    onReduceTimeList: () -> Unit = {},
    onValueChange: (Int) -> Unit = {}
) {
    val dosage = rememberSaveable { mutableStateOf(default) }
    Row(
    ) {
        PickerButton(
            size = height,
            drawable = R.drawable.ic_arrow_left,
            enabled = dosage.value > min,
            onClick = {
                if (dosage.value > min) dosage.value--
                onReduceTimeList()
                onValueChange(dosage.value)
            }
        )

        Text(
            text = dosage.value.toString(),
            fontSize = (height.value / 2).sp,
            modifier = Modifier
                .padding(10.dp)
                .height(IntrinsicSize.Max)
                .align(CenterVertically)
        )

        PickerButton(
            size = height,
            drawable = R.drawable.ic_arrow_right,
            enabled = dosage.value < max,
            onClick = {
                if (dosage.value < max) dosage.value++
                onValueChange(dosage.value)
            }
        )

    }
}