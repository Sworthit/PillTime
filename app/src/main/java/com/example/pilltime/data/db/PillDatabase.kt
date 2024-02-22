package com.example.pilltime.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pilltime.data.Converters
import com.example.pilltime.data.entity.PillEntity

@Database(entities = [PillEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class PillDatabase : RoomDatabase() {
    abstract val pillDAO : PillDAO
}