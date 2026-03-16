package com.example.mydairy_app.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mydairy_app.data.local.dao.EntryDao
import com.example.mydairy_app.data.local.dao.EntryTagDao
import com.example.mydairy_app.data.local.dao.TagDao
import com.example.mydairy_app.data.local.entity.EntryEntity
import com.example.mydairy_app.data.local.entity.EntryFts
import com.example.mydairy_app.data.local.entity.EntryTagCrossRef
import com.example.mydairy_app.data.local.entity.TagEntity

@Database(
    entities = [
        EntryEntity::class,
        TagEntity::class,
        EntryTagCrossRef::class,
        EntryFts::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao
    abstract fun tagDao(): TagDao
    abstract fun entryTagDao(): EntryTagDao
}
