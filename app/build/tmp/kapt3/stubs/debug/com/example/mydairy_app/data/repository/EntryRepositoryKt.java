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

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0002\u001a\f\u0010\u0003\u001a\u00020\u0004*\u00020\u0001H\u0002\u001a\f\u0010\u0005\u001a\u00020\u0004*\u00020\u0001H\u0002\u00a8\u0006\u0006"}, d2 = {"toDomain", "Lcom/example/mydairy_app/domain/model/Entry;", "Lcom/example/mydairy_app/data/local/entity/EntryWithTags;", "toEntity", "Lcom/example/mydairy_app/data/local/entity/EntryEntity;", "toEntityForInsert", "app_debug"})
public final class EntryRepositoryKt {
    
    private static final com.example.mydairy_app.domain.model.Entry toDomain(com.example.mydairy_app.data.local.entity.EntryWithTags $this$toDomain) {
        return null;
    }
    
    private static final com.example.mydairy_app.data.local.entity.EntryEntity toEntity(com.example.mydairy_app.domain.model.Entry $this$toEntity) {
        return null;
    }
    
    private static final com.example.mydairy_app.data.local.entity.EntryEntity toEntityForInsert(com.example.mydairy_app.domain.model.Entry $this$toEntityForInsert) {
        return null;
    }
}