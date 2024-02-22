package com.example.pilltime.feature.pilllist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class PillListViewModel @Inject constructor(
    private val getPillsUseCase: GetPillsUseCase
) : ViewModel() {
    var state by mutableStateOf(PillListState())
        private set

    init {
        loadPills()
    }

    private fun loadPills() {
        viewModelScope.launch {
            getPillsUseCase.getPills().collect { pillList ->
                state = state.copy(
                    pills = pillList
                )
            }
        }
    }
}