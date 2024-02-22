package com.example.pilltime.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pilltime.data.repository.PillRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: PillRepository
) : ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    init {
        getGreetings()
        getUserName()
        loadPills()
    }

    fun getUserName() {
        state = state.copy(userName = "Ania")
    }

    fun getGreetings() {
        state = state.copy(greetings = "Hello")
    }

    fun loadPills() {
        viewModelScope.launch {
            repository.getAll().onEach { pillList ->
                state = state.copy(pills = pillList)
            }.launchIn(viewModelScope)
        }
    }
}