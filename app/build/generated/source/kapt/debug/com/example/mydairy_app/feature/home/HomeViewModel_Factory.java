package com.example.mydairy_app.feature.home;

import com.example.mydairy_app.data.repository.EntryRepository;
import com.example.mydairy_app.data.repository.TagRepository;
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

  private final Provider<TagRepository> tagRepositoryProvider;

  public HomeViewModel_Factory(Provider<EntryRepository> entryRepositoryProvider,
      Provider<TagRepository> tagRepositoryProvider) {
    this.entryRepositoryProvider = entryRepositoryProvider;
    this.tagRepositoryProvider = tagRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(entryRepositoryProvider.get(), tagRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<EntryRepository> entryRepositoryProvider,
      Provider<TagRepository> tagRepositoryProvider) {
    return new HomeViewModel_Factory(entryRepositoryProvider, tagRepositoryProvider);
  }

  public static HomeViewModel newInstance(EntryRepository entryRepository,
      TagRepository tagRepository) {
    return new HomeViewModel(entryRepository, tagRepository);
  }
}
