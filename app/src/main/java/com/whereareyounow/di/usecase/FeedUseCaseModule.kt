package com.whereareyounow.di.usecase

import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.usecase.feed.CreateFeedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FeedUseCaseModule {

    @Provides
    fun provideCreateFeedUseCase(
        repository: FeedRepository
    ): CreateFeedUseCase {
        return CreateFeedUseCase(repository)
    }
}