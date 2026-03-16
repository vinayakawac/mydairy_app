package com.example.mydairy_app.data.repository

import androidx.room.withTransaction
import com.example.mydairy_app.core.database.AppDatabase
import com.example.mydairy_app.data.local.dao.EntryDao
import com.example.mydairy_app.data.local.dao.EntryTagDao
import com.example.mydairy_app.data.local.entity.EntryEntity
import com.example.mydairy_app.data.local.entity.EntryTagCrossRef
import com.example.mydairy_app.data.local.entity.EntryWithTags
import com.example.mydairy_app.domain.model.Entry
import com.example.mydairy_app.domain.model.Tag
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface EntryRepository {
    fun getAllEntries(): Flow<List<Entry>>
    fun getEntriesByDate(dateMillisStart: Long, dateMillisEnd: Long): Flow<List<Entry>>
    fun searchEntries(query: String): Flow<List<Entry>>
    suspend fun getEntryById(id: Long): Entry?
    suspend fun insertEntry(entry: Entry, tagIds: Set<Long>): Long
    suspend fun updateEntry(entry: Entry, tagIds: Set<Long>): Unit
    suspend fun deleteEntryById(entryId: Long): Unit
}

@Singleton
class DefaultEntryRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    private val entryDao: EntryDao,
    private val entryTagDao: EntryTagDao,
) : EntryRepository {

    override fun getAllEntries(): Flow<List<Entry>> {
        return entryDao.getAllEntries().map { entries ->
            entries.map(EntryWithTags::toDomain)
        }
    }

    override fun getEntriesByDate(dateMillisStart: Long, dateMillisEnd: Long): Flow<List<Entry>> {
        return entryDao.getEntriesByDate(dateMillisStart, dateMillisEnd).map { entries ->
            entries.map(EntryWithTags::toDomain)
        }
    }

    override fun searchEntries(query: String): Flow<List<Entry>> {
        val normalizedQuery = query.trim()
        if (normalizedQuery.isEmpty()) {
            return getAllEntries()
        }

        return entryDao.searchEntries(normalizedQuery).map { entries ->
            entries.map(EntryWithTags::toDomain)
        }
    }

    override suspend fun getEntryById(id: Long): Entry? {
        return entryDao.getEntryById(id)?.toDomain()
    }

    override suspend fun insertEntry(entry: Entry, tagIds: Set<Long>): Long {
        return appDatabase.withTransaction {
            val insertedEntryId = entryDao.insertEntry(entry.toEntityForInsert())
            updateEntryTags(entryId = insertedEntryId, tagIds = tagIds)
            insertedEntryId
        }
    }

    override suspend fun updateEntry(entry: Entry, tagIds: Set<Long>): Unit {
        appDatabase.withTransaction {
            entryDao.updateEntry(entry.toEntity())
            updateEntryTags(entryId = entry.id, tagIds = tagIds)
        }
    }

    override suspend fun deleteEntryById(entryId: Long): Unit {
        appDatabase.withTransaction {
            entryTagDao.deleteCrossRefsForEntry(entryId)
            entryDao.deleteEntryById(entryId)
        }
    }

    private suspend fun updateEntryTags(entryId: Long, tagIds: Set<Long>): Unit {
        entryTagDao.deleteCrossRefsForEntry(entryId)
        val refs = tagIds.map { tagId ->
            EntryTagCrossRef(entryId = entryId, tagId = tagId)
        }
        if (refs.isNotEmpty()) {
            entryTagDao.insertCrossRefs(refs)
        }
    }
}

private fun EntryWithTags.toDomain(): Entry {
    return Entry(
        id = entry.id,
        title = entry.title,
        body = entry.body,
        createdAt = entry.createdAt,
        updatedAt = entry.updatedAt,
        photoPaths = entry.photoPaths,
        tags = tags.map { tag ->
            Tag(id = tag.id, name = tag.name)
        },
    )
}

private fun Entry.toEntity(): EntryEntity {
    return EntryEntity(
        id = id,
        title = title,
        body = body,
        createdAt = createdAt,
        updatedAt = updatedAt,
        photoPaths = photoPaths,
    )
}

private fun Entry.toEntityForInsert(): EntryEntity {
    return EntryEntity(
        title = title,
        body = body,
        createdAt = createdAt,
        updatedAt = updatedAt,
        photoPaths = photoPaths,
    )
}
