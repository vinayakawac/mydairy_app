package com.example.mydairy_app.feature.home;

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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<EntryRepository> entryRepositoryProvider;

  public HomeViewModel_Factory(Provider<EntryRepository> entryRepositoryProvider) {
    this.entryRepositoryProvider = entryRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(entryRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<EntryRepository> entryRepositoryProvider) {
    return new HomeViewModel_Factory(entryRepositoryProvider);
  }

  public static HomeViewModel newInstance(EntryRepository entryRepository) {
    return new HomeViewModel(entryRepository);
  }
}
