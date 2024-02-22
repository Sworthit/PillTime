package com.example.pilltime.feature.pilllist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class PillListViewModelFactory @Inject constructor(private val getPillsUseCase: GetPillsUseCase) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = PillListViewModel(getPillsUseCase) as T
}