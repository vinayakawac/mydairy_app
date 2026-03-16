package com.example.mydairy_app.feature.editor;

import androidx.lifecycle.SavedStateHandle;
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
public final class EditorViewModel_Factory implements Factory<EditorViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<EntryRepository> entryRepositoryProvider;

  private final Provider<TagRepository> tagRepositoryProvider;

  public EditorViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<EntryRepository> entryRepositoryProvider,
      Provider<TagRepository> tagRepositoryProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.entryRepositoryProvider = entryRepositoryProvider;
    this.tagRepositoryProvider = tagRepositoryProvider;
  }

  @Override
  public EditorViewModel get() {
    return newInstance(savedStateHandleProvider.get(), entryRepositoryProvider.get(), tagRepositoryProvider.get());
  }

  public static EditorViewModel_Factory create(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<EntryRepository> entryRepositoryProvider,
      Provider<TagRepository> tagRepositoryProvider) {
    return new EditorViewModel_Factory(savedStateHandleProvider, entryRepositoryProvider, tagRepositoryProvider);
  }

  public static EditorViewModel newInstance(SavedStateHandle savedStateHandle,
      EntryRepository entryRepository, TagRepository tagRepository) {
    return new EditorViewModel(savedStateHandle, entryRepository, tagRepository);
  }
}
