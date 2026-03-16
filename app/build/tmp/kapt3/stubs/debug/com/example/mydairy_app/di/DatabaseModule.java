package com.example.mydairy_app.di;

import android.content.Context;
import androidx.room.Room;
import com.example.mydairy_app.core.database.AppDatabase;
import com.example.mydairy_app.data.local.dao.EntryDao;
import com.example.mydairy_app.data.local.dao.EntryTagDao;
import com.example.mydairy_app.data.local.dao.TagDao;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0006H\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000b\u001a\u00020\u0006H\u0007J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\u0006H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/example/mydairy_app/di/DatabaseModule;", "", "()V", "DATABASE_NAME", "", "provideAppDatabase", "Lcom/example/mydairy_app/core/database/AppDatabase;", "context", "Landroid/content/Context;", "provideEntryDao", "Lcom/example/mydairy_app/data/local/dao/EntryDao;", "database", "provideEntryTagDao", "Lcom/example/mydairy_app/data/local/dao/EntryTagDao;", "provideTagDao", "Lcom/example/mydairy_app/data/local/dao/TagDao;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class DatabaseModule {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String DATABASE_NAME = "mydiary.db";
    @org.jetbrains.annotations.NotNull()
    public static final com.example.mydairy_app.di.DatabaseModule INSTANCE = null;
    
    private DatabaseModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.example.mydairy_app.core.database.AppDatabase provideAppDatabase(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.example.mydairy_app.data.local.dao.EntryDao provideEntryDao(@org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.core.database.AppDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.example.mydairy_app.data.local.dao.TagDao provideTagDao(@org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.core.database.AppDatabase database) {
        return null;
    }
    
    @dagger.Provides()
    @org.jetbrains.annotations.NotNull()
    public final com.example.mydairy_app.data.local.dao.EntryTagDao provideEntryTagDao(@org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.core.database.AppDatabase database) {
        return null;
    }
}