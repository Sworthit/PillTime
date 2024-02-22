package com.example.pilltime.feature.addpill

import com.example.pilltime.data.entity.PillEntity
import com.example.pilltime.data.repository.PillRepository
import javax.inject.Inject

class AddPillUseCase @Inject constructor(private val repository: PillRepository){
    suspend fun addPill(pill: PillEntity): Long {
       return repository.insertPill(pill)
    }
}