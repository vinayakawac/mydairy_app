package com.example.mydairy_app.di

import com.example.mydairy_app.data.repository.DefaultEntryRepository
import com.example.mydairy_app.data.repository.DefaultTagRepository
import com.example.mydairy_app.data.repository.EntryRepository
import com.example.mydairy_app.data.repository.TagRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindEntryRepository(impl: DefaultEntryRepository): EntryRepository

    @Binds
    @Singleton
    abstract fun bindTagRepository(impl: DefaultTagRepository): TagRepository
}
