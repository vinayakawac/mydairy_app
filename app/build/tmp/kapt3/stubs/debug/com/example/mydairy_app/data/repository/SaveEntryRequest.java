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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u001a\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B[\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\n\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\r\u00a2\u0006\u0002\u0010\u000eJ\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0014J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\t\u0010\u001f\u001a\u00020\u0005H\u00c6\u0003J\t\u0010 \u001a\u00020\u0003H\u00c6\u0003J\t\u0010!\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00050\nH\u00c6\u0003J\u000f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00050\nH\u00c6\u0003J\u000f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00030\rH\u00c6\u0003Jt\u0010%\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\n2\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\rH\u00c6\u0001\u00a2\u0006\u0002\u0010&J\u0013\u0010\'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010*\u001a\u00020+H\u00d6\u0001J\t\u0010,\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0015\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0015\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00030\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0010R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0012\u00a8\u0006-"}, d2 = {"Lcom/example/mydairy_app/data/repository/SaveEntryRequest;", "", "entryId", "", "title", "", "body", "createdAt", "updatedAt", "existingPhotoPaths", "", "newPhotoSourceUris", "tagIds", "", "(Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;JJLjava/util/List;Ljava/util/List;Ljava/util/Set;)V", "getBody", "()Ljava/lang/String;", "getCreatedAt", "()J", "getEntryId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getExistingPhotoPaths", "()Ljava/util/List;", "getNewPhotoSourceUris", "getTagIds", "()Ljava/util/Set;", "getTitle", "getUpdatedAt", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "(Ljava/lang/Long;Ljava/lang/String;Ljava/lang/String;JJLjava/util/List;Ljava/util/List;Ljava/util/Set;)Lcom/example/mydairy_app/data/repository/SaveEntryRequest;", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
public final class SaveEntryRequest {
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long entryId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String title = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String body = null;
    private final long createdAt = 0L;
    private final long updatedAt = 0L;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> existingPhotoPaths = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> newPhotoSourceUris = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.Long> tagIds = null;
    
    public SaveEntryRequest(@org.jetbrains.annotations.Nullable()
    java.lang.Long entryId, @org.jetbrains.annotations.Nullable()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String body, long createdAt, long updatedAt, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> existingPhotoPaths, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> newPhotoSourceUris, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.Long> tagIds) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getEntryId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBody() {
        return null;
    }
    
    public final long getCreatedAt() {
        return 0L;
    }
    
    public final long getUpdatedAt() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getExistingPhotoPaths() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getNewPhotoSourceUris() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.Long> getTagIds() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    public final long component4() {
        return 0L;
    }
    
    public final long component5() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.Long> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.mydairy_app.data.repository.SaveEntryRequest copy(@org.jetbrains.annotations.Nullable()
    java.lang.Long entryId, @org.jetbrains.annotations.Nullable()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String body, long createdAt, long updatedAt, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> existingPhotoPaths, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> newPhotoSourceUris, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.Long> tagIds) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}