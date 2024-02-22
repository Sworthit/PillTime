package com.example.pilltime.feature.addpill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pilltime.data.repository.PillRepository
import javax.inject.Inject

class AddPillViewModelFactory @Inject constructor(private val addPillUseCase: AddPillUseCase) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = AddPillViewModel(addPillUseCase) as T
}