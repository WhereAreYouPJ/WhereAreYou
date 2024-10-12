package com.whereareyounow.di.usecase

import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.usecase.feed.BookmarkFeedUseCase
import com.whereareyounow.domain.usecase.feed.CreateFeedUseCase
import com.whereareyounow.domain.usecase.feed.DeleteFeedBookmarkUseCase
import com.whereareyounow.domain.usecase.feed.DeleteFeedUseCase
import com.whereareyounow.domain.usecase.feed.GetBookmarkedFeedUseCase
import com.whereareyounow.domain.usecase.feed.GetDetailFeedUseCase
import com.whereareyounow.domain.usecase.feed.GetFeedListUseCase
import com.whereareyounow.domain.usecase.feed.GetHidedFeedUseCase
import com.whereareyounow.domain.usecase.feed.HideFeedUseCase
import com.whereareyounow.domain.usecase.feed.ModifyFeedUseCase
import com.whereareyounow.domain.usecase.feed.RestoreHidedFeedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FeedUseCaseModule {

    @Provides
    fun provideModifyFeedUseCase(
        repository: FeedRepository
    ) = ModifyFeedUseCase(repository)

    @Provides
    fun provideCreateFeedUseCase(
        repository: FeedRepository
    ) = CreateFeedUseCase(repository)

    @Provides
    fun provideDeleteFeedUseCase(
        repository: FeedRepository
    ) = DeleteFeedUseCase(repository)

    @Provides
    fun provideGetFeedListUseCase(
        repository: FeedRepository
    ) = GetFeedListUseCase(repository)

    @Provides
    fun provideGetDetailFeedUseCase(
        repository: FeedRepository
    ) = GetDetailFeedUseCase(repository)

    @Provides
    fun provideGetHidedFeedUseCase(
        repository: FeedRepository
    ) = GetHidedFeedUseCase(repository)

    @Provides
    fun provideHideFeedUseCase(
        repository: FeedRepository
    ) = HideFeedUseCase(repository)

    @Provides
    fun provideRestoreHidedFeedUseCase(
        repository: FeedRepository
    ) = RestoreHidedFeedUseCase(repository)

    @Provides
    fun provideGetBookmarkedFeedUseCase(
        repository: FeedRepository
    ) = GetBookmarkedFeedUseCase(repository)

    @Provides
    fun provideBookmarkFeedUseCase(
        repository: FeedRepository
    ) = BookmarkFeedUseCase(repository)

    @Provides
    fun provideDeleteFeedBookmarkUseCase(
        repository: FeedRepository
    ) = DeleteFeedBookmarkUseCase(repository)
}
