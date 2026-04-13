package com.example.mydairy_app.data.repository

import com.example.mydairy_app.data.local.dao.TagDao
import com.example.mydairy_app.data.local.entity.TagEntity
import com.example.mydairy_app.domain.model.Tag
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface TagRepository {
    fun getAllTags(): Flow<List<Tag>>
    suspend fun insertTag(name: String): Long
    suspend fun updateTag(tag: Tag): Unit
    suspend fun deleteTag(tag: Tag): Unit
    suspend fun getTagsByIds(tagIds: Set<Long>): List<Tag>
    suspend fun getTagByName(name: String): Tag?
}

@Singleton
class DefaultTagRepository @Inject constructor(
    private val tagDao: TagDao,
) : TagRepository {

    override fun getAllTags(): Flow<List<Tag>> {
        return tagDao.getAllTags().map { tags ->
            tags.map(TagEntity::toDomain)
        }
    }

    override suspend fun insertTag(name: String): Long {
        return tagDao.insertTag(TagEntity(name = name))
    }

    override suspend fun updateTag(tag: Tag): Unit {
        tagDao.updateTag(tag.toEntity())
    }

    override suspend fun deleteTag(tag: Tag): Unit {
        tagDao.deleteTag(tag.toEntity())
    }

    override suspend fun getTagsByIds(tagIds: Set<Long>): List<Tag> {
        if (tagIds.isEmpty()) {
            return emptyList()
        }

        return tagDao.getTagsByIds(tagIds.toList()).map(TagEntity::toDomain)
    }

    override suspend fun getTagByName(name: String): Tag? {
        val normalizedName = name.trim()
        if (normalizedName.isEmpty()) {
            return null
        }

        return tagDao.getTagByName(normalizedName)?.toDomain()
    }
}

private fun TagEntity.toDomain(): Tag {
    return Tag(
        id = id,
        name = name,
    )
}

private fun Tag.toEntity(): TagEntity {
    return TagEntity(
        id = id,
        name = name,
    )
}
