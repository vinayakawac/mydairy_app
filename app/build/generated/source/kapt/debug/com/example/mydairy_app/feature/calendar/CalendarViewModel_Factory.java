package com.example.mydairy_app.feature.calendar;

import com.example.mydairy_app.data.repository.EntryRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class CalendarViewModel_Factory implements Factory<CalendarViewModel> {
  private final Provider<EntryRepository> entryRepositoryProvider;

  public CalendarViewModel_Factory(Provider<EntryRepository> entryRepositoryProvider) {
    this.entryRepositoryProvider = entryRepositoryProvider;
  }

  @Override
  public CalendarViewModel get() {
    return newInstance(entryRepositoryProvider.get());
  }

  public static CalendarViewModel_Factory create(
      Provider<EntryRepository> entryRepositoryProvider) {
    return new CalendarViewModel_Factory(entryRepositoryProvider);
  }

  public static CalendarViewModel newInstance(EntryRepository entryRepository) {
    return new CalendarViewModel(entryRepository);
  }
}
