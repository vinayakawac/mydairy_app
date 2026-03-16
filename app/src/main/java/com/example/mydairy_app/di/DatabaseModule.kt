package com.example.mydairy_app.di

import android.content.Context
import androidx.room.Room
import com.example.mydairy_app.core.database.AppDatabase
import com.example.mydairy_app.data.local.dao.EntryDao
import com.example.mydairy_app.data.local.dao.EntryTagDao
import com.example.mydairy_app.data.local.dao.TagDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val DATABASE_NAME: String = "mydiary.db"

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME,
        ).build()
    }

    @Provides
    fun provideEntryDao(database: AppDatabase): EntryDao {
        return database.entryDao()
    }

    @Provides
    fun provideTagDao(database: AppDatabase): TagDao {
        return database.tagDao()
    }

    @Provides
    fun provideEntryTagDao(database: AppDatabase): EntryTagDao {
        return database.entryTagDao()
    }
}
