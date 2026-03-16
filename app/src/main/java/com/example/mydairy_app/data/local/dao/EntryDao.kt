package com.example.mydairy_app.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.mydairy_app.data.local.entity.EntryEntity
import com.example.mydairy_app.data.local.entity.EntryWithTags
import kotlinx.coroutines.flow.Flow

@Dao
interface EntryDao {
    @Transaction
    @Query("SELECT * FROM entries ORDER BY createdAt DESC")
    fun getAllEntries(): Flow<List<EntryWithTags>>

    @Transaction
    @Query(
        "SELECT * FROM entries WHERE createdAt BETWEEN :dateMillisStart AND :dateMillisEnd ORDER BY createdAt DESC",
    )
    fun getEntriesByDate(dateMillisStart: Long, dateMillisEnd: Long): Flow<List<EntryWithTags>>

    @Transaction
    @Query("SELECT * FROM entries WHERE id = :id LIMIT 1")
    suspend fun getEntryById(id: Long): EntryWithTags?

    @Transaction
    @Query(
        """
        SELECT entries.*
        FROM entries
        INNER JOIN entries_fts ON entries.rowid = entries_fts.rowid
        WHERE entries_fts MATCH :query
        ORDER BY entries.createdAt DESC
        """,
    )
    fun searchEntries(query: String): Flow<List<EntryWithTags>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: EntryEntity): Long

    @Update
    suspend fun updateEntry(entry: EntryEntity): Int

    @Delete
    suspend fun deleteEntry(entry: EntryEntity): Int

    @Query("DELETE FROM entries WHERE id = :entryId")
    suspend fun deleteEntryById(entryId: Long): Int
}
