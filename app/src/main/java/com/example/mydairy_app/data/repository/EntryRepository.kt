package com.example.mydairy_app.data.repository

import android.content.Context
import android.net.Uri
import androidx.room.withTransaction
import com.example.mydairy_app.core.util.FileUtil
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
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class SaveEntryRequest(
    val entryId: Long?,
    val title: String?,
    val body: String,
    val createdAt: Long,
    val updatedAt: Long,
    val existingPhotoPaths: List<String>,
    val newPhotoSourceUris: List<String>,
    val tagIds: Set<Long>,
)

interface EntryRepository {
    fun getAllEntries(): Flow<List<Entry>>
    fun getEntriesByDate(dateMillisStart: Long, dateMillisEnd: Long): Flow<List<Entry>>
    fun searchEntries(query: String): Flow<List<Entry>>
    suspend fun getEntryById(id: Long): Entry?
    suspend fun createCameraOutputUri(entryId: Long?): String
    suspend fun saveFromEditor(request: SaveEntryRequest): Long
    suspend fun insertEntry(entry: Entry, tagIds: Set<Long>): Long
    suspend fun updateEntry(entry: Entry, tagIds: Set<Long>): Unit
    suspend fun deleteEntryById(entryId: Long): Unit
}

@Singleton
class DefaultEntryRepository @Inject constructor(
    @ApplicationContext private val context: Context,
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
        val ftsQuery = buildFtsMatchQuery(normalizedQuery)
        if (ftsQuery == null) {
            return getAllEntries()
        }

        return entryDao.searchEntries(ftsQuery).map { entries ->
            entries.map(EntryWithTags::toDomain)
        }
    }

    override suspend fun getEntryById(id: Long): Entry? {
        return entryDao.getEntryById(id)?.toDomain()
    }

    override suspend fun createCameraOutputUri(entryId: Long?): String {
        val targetDirectoryId = entryId ?: createTemporaryEntryDirectoryId()
        return FileUtil.createCameraOutputUri(
            context = context,
            entryId = targetDirectoryId,
        ).toString()
    }

    override suspend fun saveFromEditor(request: SaveEntryRequest): Long {
        val normalizedTitle = request.title
            ?.trim()
            ?.takeIf(String::isNotEmpty)

        return if (request.entryId == null) {
            insertFromEditor(
                request = request,
                normalizedTitle = normalizedTitle,
            )
        } else {
            updateFromEditor(
                request = request,
                normalizedTitle = normalizedTitle,
            )
        }
    }

    override suspend fun insertEntry(entry: Entry, tagIds: Set<Long>): Long {
        return appDatabase.withTransaction {
            val insertedEntryId = entryDao.insertEntry(entry.toEntityForInsert())
            syncEntryFts(
                entryId = insertedEntryId,
                title = entry.title,
                body = entry.body,
            )
            updateEntryTags(entryId = insertedEntryId, tagIds = tagIds)
            insertedEntryId
        }
    }

    override suspend fun updateEntry(entry: Entry, tagIds: Set<Long>): Unit {
        appDatabase.withTransaction {
            entryDao.updateEntry(entry.toEntity())
            syncEntryFts(
                entryId = entry.id,
                title = entry.title,
                body = entry.body,
            )
            updateEntryTags(entryId = entry.id, tagIds = tagIds)
        }
    }

    override suspend fun deleteEntryById(entryId: Long): Unit {
        FileUtil.deleteEntryDirectory(
            context = context,
            entryId = entryId,
        )
        appDatabase.withTransaction {
            entryTagDao.deleteCrossRefsForEntry(entryId)
            entryDao.deleteFtsEntry(entryId)
            entryDao.deleteEntryById(entryId)
        }
    }

    private suspend fun insertFromEditor(request: SaveEntryRequest, normalizedTitle: String?): Long {
        val insertedId = appDatabase.withTransaction {
            val insertedEntryId = entryDao.insertEntry(
                EntryEntity(
                    title = normalizedTitle,
                    body = request.body,
                    createdAt = request.createdAt,
                    updatedAt = request.updatedAt,
                    photoPaths = request.existingPhotoPaths,
                ),
            )
            syncEntryFts(
                entryId = insertedEntryId,
                title = normalizedTitle,
                body = request.body,
            )
            updateEntryTags(entryId = insertedEntryId, tagIds = request.tagIds)
            insertedEntryId
        }

        val copiedNewPhotos = copyPhotoUrisToEntryDirectory(
            sourceUris = request.newPhotoSourceUris,
            entryId = insertedId,
        )

        if (copiedNewPhotos.isNotEmpty()) {
            val updatedPhotoPaths = request.existingPhotoPaths + copiedNewPhotos
            appDatabase.withTransaction {
                entryDao.updateEntry(
                    EntryEntity(
                        id = insertedId,
                        title = normalizedTitle,
                        body = request.body,
                        createdAt = request.createdAt,
                        updatedAt = request.updatedAt,
                        photoPaths = updatedPhotoPaths,
                    ),
                )
            }
        }

        return insertedId
    }

    private suspend fun updateFromEditor(request: SaveEntryRequest, normalizedTitle: String?): Long {
        val entryId = request.entryId ?: return INVALID_ENTRY_ID
        val existingDbEntry = entryDao.getEntryById(entryId)
        val removedPhotoPaths = existingDbEntry
            ?.entry
            ?.photoPaths
            .orEmpty()
            .filterNot { path -> request.existingPhotoPaths.contains(path) }

        deletePhotoFiles(removedPhotoPaths)

        val copiedNewPhotos = copyPhotoUrisToEntryDirectory(
            sourceUris = request.newPhotoSourceUris,
            entryId = entryId,
        )
        val updatedPhotoPaths = request.existingPhotoPaths + copiedNewPhotos

        appDatabase.withTransaction {
            entryDao.updateEntry(
                EntryEntity(
                    id = entryId,
                    title = normalizedTitle,
                    body = request.body,
                    createdAt = request.createdAt,
                    updatedAt = request.updatedAt,
                    photoPaths = updatedPhotoPaths,
                ),
            )
            syncEntryFts(
                entryId = entryId,
                title = normalizedTitle,
                body = request.body,
            )
            updateEntryTags(entryId = entryId, tagIds = request.tagIds)
        }

        return entryId
    }

    private suspend fun copyPhotoUrisToEntryDirectory(sourceUris: List<String>, entryId: Long): List<String> {
        return sourceUris
            .distinct()
            .mapNotNull { sourceUri ->
                val parsedUri = Uri.parse(sourceUri)
                runCatching {
                    FileUtil.copyPhotoToEntryDirectory(
                        context = context,
                        sourceUri = parsedUri,
                        entryId = entryId,
                    )
                }.getOrNull()
            }
    }

    private fun deletePhotoFiles(paths: List<String>): Unit {
        paths.forEach { path ->
            val targetFile = File(path)
            if (targetFile.exists()) {
                targetFile.delete()
            }
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

    private suspend fun syncEntryFts(entryId: Long, title: String?, body: String): Unit {
        entryDao.deleteFtsEntry(entryId)
        entryDao.insertFtsEntry(
            entryId = entryId,
            title = title,
            body = body,
        )
    }

    private fun buildFtsMatchQuery(query: String): String? {
        val tokens = query
            .split(FTS_TOKEN_SPLIT_REGEX)
            .map(::sanitizeFtsToken)
            .filter(String::isNotEmpty)
            .map { token ->
                "\"$token\"*"
            }

        return if (tokens.isEmpty()) {
            null
        } else {
            tokens.joinToString(separator = FTS_TOKEN_JOINER)
        }
    }

    private fun sanitizeFtsToken(token: String): String {
        return token
            .replace(FTS_DOUBLE_QUOTE, EMPTY_STRING)
            .replace(FTS_ASTERISK, EMPTY_STRING)
            .replace(FTS_MINUS, EMPTY_STRING)
            .trim()
    }

    private fun createTemporaryEntryDirectoryId(): Long {
        return -System.currentTimeMillis()
    }

    private companion object {
        const val INVALID_ENTRY_ID: Long = -1L
        const val EMPTY_STRING: String = ""
        const val FTS_DOUBLE_QUOTE: String = "\""
        const val FTS_ASTERISK: String = "*"
        const val FTS_MINUS: String = "-"
        const val FTS_TOKEN_JOINER: String = " AND "
        val FTS_TOKEN_SPLIT_REGEX: Regex = Regex("\\s+")
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
