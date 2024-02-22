package com.example.pilltime.feature.pilllist

import com.example.pilltime.data.entity.PillEntity
import com.example.pilltime.data.repository.PillRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPillsUseCase @Inject constructor(
    private val repository: PillRepository
) {
    fun getPills(): Flow<List<PillEntity>> {
        return repository.getAll()
    }
}