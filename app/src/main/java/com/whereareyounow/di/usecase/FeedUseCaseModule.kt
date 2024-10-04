package com.whereareyounow.di.usecase

import com.whereareyounow.domain.repository.FCMRepository
import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.usecase.fcm.DeleteFCMTokenUseCase
import com.whereareyounow.domain.usecase.fcm.UpdateFCMTokenUseCase
import com.whereareyounow.domain.usecase.feed.GetFeedBookMarkUseCase
import com.whereareyounow.domain.usecase.feed.CreateFeedUseCase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FeedUseCaseModule {

    fun provideCreateFeedUseCase(
        repository: FeedRepository
    ): CreateFeedUseCase {
        return CreateFeedUseCase(repository)
    }
}
