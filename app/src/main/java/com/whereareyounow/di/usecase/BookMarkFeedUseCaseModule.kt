package com.whereareyounow.di.usecase

import com.whereareyounow.domain.repository.BookMarkFeedRepository
import com.whereareyounow.domain.usecase.feedbookmark.AddFeedBookMarkUseCase
import com.whereareyounow.domain.usecase.feedbookmark.GetFeedBookMarkUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BookMarkFeedUseCaseModule {

    @Provides
    fun provideGetFeedBookMarkUseCase(
        repository: BookMarkFeedRepository
    ): GetFeedBookMarkUseCase {
        return GetFeedBookMarkUseCase(repository)
    }

    @Provides
    fun provideAddFeedBookMarkUseCase(
        repository: BookMarkFeedRepository
    ): AddFeedBookMarkUseCase {
        return AddFeedBookMarkUseCase(repository)
    }

}