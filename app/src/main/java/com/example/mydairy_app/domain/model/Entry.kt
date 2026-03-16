package com.example.mydairy_app.domain.model

data class Entry(
    val id: Long,
    val title: String?,
    val body: String,
    val createdAt: Long,
    val updatedAt: Long,
    val photoPaths: List<String>,
    val tags: List<Tag>,
)
