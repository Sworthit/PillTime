package com.example.pilltime.feature.pilllist

import com.example.pilltime.data.entity.PillEntity

data class PillListState(
    val pills: List<PillEntity> = emptyList()
)
