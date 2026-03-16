package com.example.mydairy_app.core.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.mydairy_app.data.local.dao.EntryDao;
import com.example.mydairy_app.data.local.dao.EntryTagDao;
import com.example.mydairy_app.data.local.dao.TagDao;
import com.example.mydairy_app.data.local.entity.EntryEntity;
import com.example.mydairy_app.data.local.entity.EntryFts;
import com.example.mydairy_app.data.local.entity.EntryTagCrossRef;
import com.example.mydairy_app.data.local.entity.TagEntity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\t"}, d2 = {"Lcom/example/mydairy_app/core/database/AppDatabase;", "Landroidx/room/RoomDatabase;", "()V", "entryDao", "Lcom/example/mydairy_app/data/local/dao/EntryDao;", "entryTagDao", "Lcom/example/mydairy_app/data/local/dao/EntryTagDao;", "tagDao", "Lcom/example/mydairy_app/data/local/dao/TagDao;", "app_debug"})
@androidx.room.Database(entities = {com.example.mydairy_app.data.local.entity.EntryEntity.class, com.example.mydairy_app.data.local.entity.TagEntity.class, com.example.mydairy_app.data.local.entity.EntryTagCrossRef.class, com.example.mydairy_app.data.local.entity.EntryFts.class}, version = 1, exportSchema = true)
@androidx.room.TypeConverters(value = {com.example.mydairy_app.core.database.Converters.class})
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    
    public AppDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.mydairy_app.data.local.dao.EntryDao entryDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.mydairy_app.data.local.dao.TagDao tagDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.example.mydairy_app.data.local.dao.EntryTagDao entryTagDao();
}