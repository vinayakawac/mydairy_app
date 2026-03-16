package com.example.mydairy_app.data.repository;

import android.content.Context;
import com.example.mydairy_app.core.database.AppDatabase;
import com.example.mydairy_app.data.local.dao.EntryDao;
import com.example.mydairy_app.data.local.dao.EntryTagDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class DefaultEntryRepository_Factory implements Factory<DefaultEntryRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<AppDatabase> appDatabaseProvider;

  private final Provider<EntryDao> entryDaoProvider;

  private final Provider<EntryTagDao> entryTagDaoProvider;

  public DefaultEntryRepository_Factory(Provider<Context> contextProvider,
      Provider<AppDatabase> appDatabaseProvider, Provider<EntryDao> entryDaoProvider,
      Provider<EntryTagDao> entryTagDaoProvider) {
    this.contextProvider = contextProvider;
    this.appDatabaseProvider = appDatabaseProvider;
    this.entryDaoProvider = entryDaoProvider;
    this.entryTagDaoProvider = entryTagDaoProvider;
  }

  @Override
  public DefaultEntryRepository get() {
    return newInstance(contextProvider.get(), appDatabaseProvider.get(), entryDaoProvider.get(), entryTagDaoProvider.get());
  }

  public static DefaultEntryRepository_Factory create(Provider<Context> contextProvider,
      Provider<AppDatabase> appDatabaseProvider, Provider<EntryDao> entryDaoProvider,
      Provider<EntryTagDao> entryTagDaoProvider) {
    return new DefaultEntryRepository_Factory(contextProvider, appDatabaseProvider, entryDaoProvider, entryTagDaoProvider);
  }

  public static DefaultEntryRepository newInstance(Context context, AppDatabase appDatabase,
      EntryDao entryDao, EntryTagDao entryTagDao) {
    return new DefaultEntryRepository(context, appDatabase, entryDao, entryTagDao);
  }
}
