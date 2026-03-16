package com.example.mydairy_app.data.repository;

import android.content.Context;
import android.net.Uri;
import com.example.mydairy_app.core.util.FileUtil;
import com.example.mydairy_app.core.database.AppDatabase;
import com.example.mydairy_app.data.local.dao.EntryDao;
import com.example.mydairy_app.data.local.dao.EntryTagDao;
import com.example.mydairy_app.data.local.entity.EntryEntity;
import com.example.mydairy_app.data.local.entity.EntryTagCrossRef;
import com.example.mydairy_app.data.local.entity.EntryWithTags;
import com.example.mydairy_app.domain.model.Entry;
import com.example.mydairy_app.domain.model.Tag;
import javax.inject.Inject;
import javax.inject.Singleton;
import dagger.hilt.android.qualifiers.ApplicationContext;
import java.io.File;
import kotlinx.coroutines.flow.Flow;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\b\u0007\u0018\u0000 42\u00020\u0001:\u00014B)\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ*\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u0082@\u00a2\u0006\u0002\u0010\u0011J\u0018\u0010\u0012\u001a\u00020\r2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096@\u00a2\u0006\u0002\u0010\u0013J\b\u0010\u0014\u001a\u00020\u0010H\u0002J\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u0010H\u0096@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010\u0018\u001a\u00020\u00162\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0002J\u0014\u0010\u001a\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\f0\u001bH\u0016J$\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\f0\u001b2\u0006\u0010\u001e\u001a\u00020\u00102\u0006\u0010\u001f\u001a\u00020\u0010H\u0016J\u0018\u0010 \u001a\u0004\u0018\u00010\u001c2\u0006\u0010!\u001a\u00020\u0010H\u0096@\u00a2\u0006\u0002\u0010\u0017J$\u0010\"\u001a\u00020\u00102\u0006\u0010#\u001a\u00020\u001c2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00100%H\u0096@\u00a2\u0006\u0002\u0010&J \u0010\'\u001a\u00020\u00102\u0006\u0010(\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\rH\u0082@\u00a2\u0006\u0002\u0010+J\u0016\u0010,\u001a\u00020\u00102\u0006\u0010(\u001a\u00020)H\u0096@\u00a2\u0006\u0002\u0010-J\u001c\u0010.\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001c0\f0\u001b2\u0006\u0010/\u001a\u00020\rH\u0016J$\u00100\u001a\u00020\u00162\u0006\u0010#\u001a\u00020\u001c2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00100%H\u0096@\u00a2\u0006\u0002\u0010&J$\u00101\u001a\u00020\u00162\u0006\u0010\u000f\u001a\u00020\u00102\f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00100%H\u0082@\u00a2\u0006\u0002\u00102J \u00103\u001a\u00020\u00102\u0006\u0010(\u001a\u00020)2\b\u0010*\u001a\u0004\u0018\u00010\rH\u0082@\u00a2\u0006\u0002\u0010+R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00065"}, d2 = {"Lcom/example/mydairy_app/data/repository/DefaultEntryRepository;", "Lcom/example/mydairy_app/data/repository/EntryRepository;", "context", "Landroid/content/Context;", "appDatabase", "Lcom/example/mydairy_app/core/database/AppDatabase;", "entryDao", "Lcom/example/mydairy_app/data/local/dao/EntryDao;", "entryTagDao", "Lcom/example/mydairy_app/data/local/dao/EntryTagDao;", "(Landroid/content/Context;Lcom/example/mydairy_app/core/database/AppDatabase;Lcom/example/mydairy_app/data/local/dao/EntryDao;Lcom/example/mydairy_app/data/local/dao/EntryTagDao;)V", "copyPhotoUrisToEntryDirectory", "", "", "sourceUris", "entryId", "", "(Ljava/util/List;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createCameraOutputUri", "(Ljava/lang/Long;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createTemporaryEntryDirectoryId", "deleteEntryById", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deletePhotoFiles", "paths", "getAllEntries", "Lkotlinx/coroutines/flow/Flow;", "Lcom/example/mydairy_app/domain/model/Entry;", "getEntriesByDate", "dateMillisStart", "dateMillisEnd", "getEntryById", "id", "insertEntry", "entry", "tagIds", "", "(Lcom/example/mydairy_app/domain/model/Entry;Ljava/util/Set;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertFromEditor", "request", "Lcom/example/mydairy_app/data/repository/SaveEntryRequest;", "normalizedTitle", "(Lcom/example/mydairy_app/data/repository/SaveEntryRequest;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveFromEditor", "(Lcom/example/mydairy_app/data/repository/SaveEntryRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchEntries", "query", "updateEntry", "updateEntryTags", "(JLjava/util/Set;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateFromEditor", "Companion", "app_debug"})
public final class DefaultEntryRepository implements com.example.mydairy_app.data.repository.EntryRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.mydairy_app.core.database.AppDatabase appDatabase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.mydairy_app.data.local.dao.EntryDao entryDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.mydairy_app.data.local.dao.EntryTagDao entryTagDao = null;
    @java.lang.Deprecated()
    public static final long INVALID_ENTRY_ID = -1L;
    @org.jetbrains.annotations.NotNull()
    private static final com.example.mydairy_app.data.repository.DefaultEntryRepository.Companion Companion = null;
    
    @javax.inject.Inject()
    public DefaultEntryRepository(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.core.database.AppDatabase appDatabase, @org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.data.local.dao.EntryDao entryDao, @org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.data.local.dao.EntryTagDao entryTagDao) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.mydairy_app.domain.model.Entry>> getAllEntries() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.mydairy_app.domain.model.Entry>> getEntriesByDate(long dateMillisStart, long dateMillisEnd) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.example.mydairy_app.domain.model.Entry>> searchEntries(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getEntryById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.mydairy_app.domain.model.Entry> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object createCameraOutputUri(@org.jetbrains.annotations.Nullable()
    java.lang.Long entryId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object saveFromEditor(@org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.data.repository.SaveEntryRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object insertEntry(@org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.domain.model.Entry entry, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.Long> tagIds, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object updateEntry(@org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.domain.model.Entry entry, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.Long> tagIds, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object deleteEntryById(long entryId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object insertFromEditor(com.example.mydairy_app.data.repository.SaveEntryRequest request, java.lang.String normalizedTitle, kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    private final java.lang.Object updateFromEditor(com.example.mydairy_app.data.repository.SaveEntryRequest request, java.lang.String normalizedTitle, kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    private final java.lang.Object copyPhotoUrisToEntryDirectory(java.util.List<java.lang.String> sourceUris, long entryId, kotlin.coroutines.Continuation<? super java.util.List<java.lang.String>> $completion) {
        return null;
    }
    
    private final void deletePhotoFiles(java.util.List<java.lang.String> paths) {
    }
    
    private final java.lang.Object updateEntryTags(long entryId, java.util.Set<java.lang.Long> tagIds, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final long createTemporaryEntryDirectoryId() {
        return 0L;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/example/mydairy_app/data/repository/DefaultEntryRepository$Companion;", "", "()V", "INVALID_ENTRY_ID", "", "app_debug"})
    static final class Companion {
        
        private Companion() {
            super();
        }
    }
}