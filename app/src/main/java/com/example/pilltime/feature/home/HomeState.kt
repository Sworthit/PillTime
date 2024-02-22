package com.example.pilltime.feature.home

import com.example.pilltime.data.entity.PillEntity

data class HomeState(
    val greetings: String = "",
    val userName: String = "",
    var pills: List<PillEntity> = emptyList()
)