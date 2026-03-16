package com.example.mydairy_app.data.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import com.example.mydairy_app.data.local.entity.EntryEntity;
import com.example.mydairy_app.data.local.entity.EntryWithTags;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fH\'J$\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\tH\'J\u0018\u0010\u0012\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0013\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f2\u0006\u0010\u0016\u001a\u00020\u0017H\'J\u0016\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0019"}, d2 = {"Lcom/example/mydairy_app/data/local/dao/EntryDao;", "", "deleteEntry", "", "entry", "Lcom/example/mydairy_app/data/local/entity/EntryEntity;", "(Lcom/example/mydairy_app/data/local/entity/EntryEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteEntryById", "entryId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllEntries", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/mydairy_app/data/local/entity/EntryWithTags;", "getEntriesByDate", "dateMillisStart", "dateMillisEnd", "getEntryById", "id", "insertEntry", "searchEntries", "query", "", "updateEntry", "app_debug"})
@androidx.room.Dao()
public abstract interface EntryDao {
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM entries ORDER BY createdAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.mydairy_app.data.local.entity.EntryWithTags>> getAllEntries();
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM entries WHERE createdAt BETWEEN :dateMillisStart AND :dateMillisEnd ORDER BY createdAt DESC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.mydairy_app.data.local.entity.EntryWithTags>> getEntriesByDate(long dateMillisStart, long dateMillisEnd);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "SELECT * FROM entries WHERE id = :id LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getEntryById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.mydairy_app.data.local.entity.EntryWithTags> $completion);
    
    @androidx.room.Transaction()
    @androidx.room.Query(value = "\n        SELECT entries.*\n        FROM entries\n        INNER JOIN entries_fts ON entries.rowid = entries_fts.rowid\n        WHERE entries_fts MATCH :query\n        ORDER BY entries.createdAt DESC\n        ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.mydairy_app.data.local.entity.EntryWithTags>> searchEntries(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertEntry(@org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.data.local.entity.EntryEntity entry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateEntry(@org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.data.local.entity.EntryEntity entry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteEntry(@org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.data.local.entity.EntryEntity entry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "DELETE FROM entries WHERE id = :entryId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteEntryById(long entryId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
}