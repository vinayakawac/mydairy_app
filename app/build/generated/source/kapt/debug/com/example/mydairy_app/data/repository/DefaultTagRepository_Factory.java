package com.example.mydairy_app.data.repository;

import com.example.mydairy_app.data.local.dao.TagDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DefaultTagRepository_Factory implements Factory<DefaultTagRepository> {
  private final Provider<TagDao> tagDaoProvider;

  public DefaultTagRepository_Factory(Provider<TagDao> tagDaoProvider) {
    this.tagDaoProvider = tagDaoProvider;
  }

  @Override
  public DefaultTagRepository get() {
    return newInstance(tagDaoProvider.get());
  }

  public static DefaultTagRepository_Factory create(Provider<TagDao> tagDaoProvider) {
    return new DefaultTagRepository_Factory(tagDaoProvider);
  }

  public static DefaultTagRepository newInstance(TagDao tagDao) {
    return new DefaultTagRepository(tagDao);
  }
}
