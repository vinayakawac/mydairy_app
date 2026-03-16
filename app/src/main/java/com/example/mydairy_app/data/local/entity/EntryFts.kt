package com.example.mydairy_app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = EntryEntity::class)
@Entity(tableName = "entries_fts")
data class EntryFts(
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "body") val body: String,
)
