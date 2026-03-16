package com.example.mydairy_app.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.mydairy_app.data.local.entity.EntryTagCrossRef;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\"\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\fH\u00a7@\u00a2\u0006\u0002\u0010\u000e\u00a8\u0006\u000f"}, d2 = {"Lcom/example/mydairy_app/data/local/dao/EntryTagDao;", "", "deleteCrossRefsForEntry", "", "entryId", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertCrossRef", "crossRef", "Lcom/example/mydairy_app/data/local/entity/EntryTagCrossRef;", "(Lcom/example/mydairy_app/data/local/entity/EntryTagCrossRef;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertCrossRefs", "", "crossRefs", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface EntryTagDao {
    
    @androidx.room.Insert(onConflict = 5)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertCrossRef(@org.jetbrains.annotations.NotNull()
    com.example.mydairy_app.data.local.entity.EntryTagCrossRef crossRef, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Insert(onConflict = 5)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertCrossRefs(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.mydairy_app.data.local.entity.EntryTagCrossRef> crossRefs, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<java.lang.Long>> $completion);
    
    @androidx.room.Query(value = "DELETE FROM entry_tag_cross_ref WHERE entryId = :entryId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteCrossRefsForEntry(long entryId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
}