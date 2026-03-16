package com.example.mydairy_app.core.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\'\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2 = {"Lcom/example/mydairy_app/core/database/AppDatabase;", "Landroidx/room/RoomDatabase;", "<init>", "()V", "app_debug"})
@androidx.room.Database(entities = {com.example.mydairy_app.core.database.BootstrapEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends androidx.room.RoomDatabase {
    
    public AppDatabase() {
        super();
    }
}