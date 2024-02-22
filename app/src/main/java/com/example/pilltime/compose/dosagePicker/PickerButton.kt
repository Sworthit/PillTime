package com.example.pilltime.compose.dosagePicker

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PickerButton(
    size: Dp = 45.dp,
    @DrawableRes drawable: Int,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    val backgroundColor = if (enabled) MaterialTheme.colorScheme.secondary else Color.LightGray

    Image(
        painter = painterResource(id = drawable),
        contentDescription = "Picker Button",
        modifier = Modifier
            .padding(8.dp)
            .background(backgroundColor, CircleShape)
            .clip(CircleShape)
            .width(size)
            .height(size)
            .clickable(
                enabled = enabled,
                onClick = { onClick() }
            )
    )
}