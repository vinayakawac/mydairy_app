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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\tJ\u0014\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000bH&J$\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b2\u0006\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0010\u001a\u00020\u0005H&J\u0018\u0010\u0011\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0012\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\tJ$\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\r2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\u0016H\u00a6@\u00a2\u0006\u0002\u0010\u0017J\u0016\u0010\u0018\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u001aH\u00a6@\u00a2\u0006\u0002\u0010\u001bJ\u001c\u0010\u001c\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u000b2\u0006\u0010\u001d\u001a\u00020\u0003H&J$\u0010\u001e\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\r2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00050\u0016H\u00a6@\u00a2\u0006\u0002\u0010\u0017\u00a8\u0006\u001f"}, d2 = {"Lcom/example/mydairy_app/data/repository/EntryRepository;", "", "createCameraOutputUri", "", "entryId", "", "(Ljava/lang/Long;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteEntryById", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllEntries", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/example/mydairy_app/domain/model/Entry;", "getEntriesByDate", "dateMillisStart", "dateMillisEnd", "getEntryById", "id", "insertEntry", "entry", "tagIds", "", "(Lcom/example/mydairy_app/domain/model/Entry;Ljava/util/Set;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveFromEditor", "request", "Lcom/example/mydairy_app/data/repository/SaveEntryRequest;", "(Lcom/example/mydairy_app/data/repository/SaveEntryRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchEntries", "query", "updateEntry", "app_debug"})
public abstract interface EntryRepository {
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.mydairy_app.domain.model.Entry>> getAllEntries();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.mydairy_app.domain.model.Entry>> getEntriesByDate(long dateMillisStart, long dateMillisEnd);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.example.mydairy_app.domain.model.Entry>> searchEntries(@org.jetbrains.annotations.NotNull()
    java.lang.String query);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getEntryById(long id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.example.mydairy_app.domain.model.Entry> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createCameraOutputUri(@org.jetbrains.annotations.Nullable()
    java.lang.Long entryId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveFromEditor(@org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.data.repository.SaveEntryRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertEntry(@org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.domain.model.Entry entry, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.Long> tagIds, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateEntry(@org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.domain.model.Entry entry, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.Long> tagIds, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteEntryById(long entryId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}