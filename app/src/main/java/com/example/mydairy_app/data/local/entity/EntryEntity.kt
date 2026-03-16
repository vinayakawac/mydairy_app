package com.example.mydairy_app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class EntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String?,
    val body: String,
    val createdAt: Long,
    val updatedAt: Long,
    val photoPaths: List<String>,
)
