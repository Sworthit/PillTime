package com.example.pilltime.data.repository

import com.example.pilltime.data.entity.PillEntity
import kotlinx.coroutines.flow.Flow

interface PillRepository {
    suspend fun insertPill(pillEntity: PillEntity): Long
    suspend fun updatePill(pillEntity: PillEntity)
    suspend fun deletePill(pillEntity: PillEntity)
    suspend fun deleteAll()
    fun getAll() : Flow<List<PillEntity>>
    fun getPill(pillId: Int): Flow<PillEntity>
    fun getPillByName(pillName: String): Flow<PillEntity>
}