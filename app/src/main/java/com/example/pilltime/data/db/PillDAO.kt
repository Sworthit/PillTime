package com.example.pilltime.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pilltime.data.entity.PillEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PillDAO {
    @Insert
    suspend fun insertPill(pillEntity: PillEntity) : Long

    @Delete
    suspend fun deletePill(pillEntity: PillEntity)

    @Query("DELETE FROM pill_table")
    suspend fun deleteAll()

    @Update
    suspend fun updatePill(pillEntity: PillEntity)

    @Query("SELECT * FROM pill_table")
    fun getAll(): Flow<List<PillEntity>>

    @Query("SELECT * FROM pill_table WHERE id =:id")
    fun getPillDetails(id: Int): Flow<PillEntity>

    @Query("SELECT * FROM pill_table WHERE name = :name")
    fun getPillByName(name: String): Flow<PillEntity>
}