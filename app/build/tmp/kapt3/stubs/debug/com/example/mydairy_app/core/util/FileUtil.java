package com.example.mydairy_app.core.util;

import android.content.Context;
import android.net.Uri;
import androidx.core.content.FileProvider;
import java.io.File;
import kotlinx.coroutines.Dispatchers;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\b\u001a\u00020\u0006H\u0002J&\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0011\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0018\u0010\u0012\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u001e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0016J\u0018\u0010\u0017\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/example/mydairy_app/core/util/FileUtil;", "", "()V", "COPY_BUFFER_SIZE", "", "FILE_EXTENSION", "", "ROOT_PHOTO_DIR", "buildPhotoFileName", "copyPhotoToEntryDirectory", "context", "Landroid/content/Context;", "sourceUri", "Landroid/net/Uri;", "entryId", "", "(Landroid/content/Context;Landroid/net/Uri;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createCameraOutputUri", "createPhotoFile", "Ljava/io/File;", "deleteEntryDirectory", "", "(Landroid/content/Context;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getEntryDirectory", "app_debug"})
public final class FileUtil {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String ROOT_PHOTO_DIR = "diary_photos";
    private static final int COPY_BUFFER_SIZE = 8192;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String FILE_EXTENSION = ".jpg";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.mydairy_app.core.util.FileUtil INSTANCE = null;
    
    private FileUtil() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object copyPhotoToEntryDirectory(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.net.Uri sourceUri, long entryId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.net.Uri createCameraOutputUri(@org.jetbrains.annotations.NotNull()
    android.content.Context context, long entryId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteEntryDirectory(@org.jetbrains.annotations.NotNull()
    android.content.Context context, long entryId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.io.File createPhotoFile(android.content.Context context, long entryId) {
        return null;
    }
    
    private final java.lang.String buildPhotoFileName() {
        return null;
    }
    
    private final java.io.File getEntryDirectory(android.content.Context context, long entryId) {
        return null;
    }
}