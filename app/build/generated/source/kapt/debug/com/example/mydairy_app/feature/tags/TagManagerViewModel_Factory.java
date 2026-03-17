package com.example.mydairy_app.feature.tags;

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
public final class TagManagerViewModel_Factory implements Factory<TagManagerViewModel> {
  private final Provider<TagRepository> tagRepositoryProvider;

  public TagManagerViewModel_Factory(Provider<TagRepository> tagRepositoryProvider) {
    this.tagRepositoryProvider = tagRepositoryProvider;
  }

  @Override
  public TagManagerViewModel get() {
    return newInstance(tagRepositoryProvider.get());
  }

  public static TagManagerViewModel_Factory create(Provider<TagRepository> tagRepositoryProvider) {
    return new TagManagerViewModel_Factory(tagRepositoryProvider);
  }

  public static TagManagerViewModel newInstance(TagRepository tagRepository) {
    return new TagManagerViewModel(tagRepository);
  }
}
