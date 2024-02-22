package com.example.pilltime.data.repository

import com.example.pilltime.data.db.PillDAO
import com.example.pilltime.data.entity.PillEntity
import kotlinx.coroutines.flow.Flow

class PillRepositoryImpl(private val dao: PillDAO) : PillRepository {
    override suspend fun insertPill(pillEntity: PillEntity): Long {
        return dao.insertPill(pillEntity)
    }

    override suspend fun updatePill(pillEntity: PillEntity) {
        dao.updatePill(pillEntity)
    }

    override suspend fun deletePill(pillEntity: PillEntity) {
        dao.deletePill(pillEntity)
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override fun getAll(): Flow<List<PillEntity>> {
        return dao.getAll()
    }

    override fun getPill(pillId: Int): Flow<PillEntity> {
        return dao.getPillDetails(pillId)
    }

    override fun getPillByName(pillName: String): Flow<PillEntity> {
        return dao.getPillByName(pillName)
    }
}