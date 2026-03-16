package com.example.mydairy_app.di;

import com.example.mydairy_app.core.database.AppDatabase;
import com.example.mydairy_app.data.local.dao.EntryTagDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
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
public final class DatabaseModule_ProvideEntryTagDaoFactory implements Factory<EntryTagDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideEntryTagDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public EntryTagDao get() {
    return provideEntryTagDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideEntryTagDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideEntryTagDaoFactory(databaseProvider);
  }

  public static EntryTagDao provideEntryTagDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideEntryTagDao(database));
  }
}
