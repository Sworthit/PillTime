package com.example.pilltime.di

import android.app.Application
import androidx.room.Room
import com.example.pilltime.data.db.PillDatabase
import com.example.pilltime.data.repository.PillRepository
import com.example.pilltime.data.repository.PillRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PillDataModule {

    @Provides
    @Singleton
    fun providePillDatabase(app: Application): PillDatabase {
        return Room.databaseBuilder(
            app,
            PillDatabase::class.java,
            "pill_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePillRepository(
        db: PillDatabase
    ) : PillRepository {
        return PillRepositoryImpl(db.pillDAO)
    }
}