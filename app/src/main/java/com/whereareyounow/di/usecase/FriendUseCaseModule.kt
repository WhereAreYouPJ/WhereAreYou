package com.whereareyounow.di.usecase

import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.usecase.friend.AcceptFriendRequestUseCase
import com.whereareyounow.domain.usecase.friend.AddFriendToFavoriteUseCase
import com.whereareyounow.domain.usecase.friend.DeleteFriendUseCase
import com.whereareyounow.domain.usecase.friend.GetFriendListUseCase
import com.whereareyounow.domain.usecase.friend.GetFriendRequestListUseCase
import com.whereareyounow.domain.usecase.friend.RefuseFriendRequestUseCase
import com.whereareyounow.domain.usecase.friend.RemoveFriendFromFavoriteUseCase
import com.whereareyounow.domain.usecase.friend.SendFriendRequestUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FriendUseCaseModule {

    @Provides
    fun provideAddFriendToFavoriteUseCase(
        repository: FriendRepository
    ) = AddFriendToFavoriteUseCase(repository)

    @Provides
    fun provideRemoveFriendFromFavoriteUseCase(
        repository: FriendRepository
    ) = RemoveFriendFromFavoriteUseCase(repository)

    @Provides
    fun provideGetFriendListUseCase(
        repository: FriendRepository
    ): GetFriendListUseCase {
        return GetFriendListUseCase(repository)
    }

    @Provides
    fun provideDeleteFriendUseCase(
        repository: FriendRepository
    ): DeleteFriendUseCase {
        return DeleteFriendUseCase(repository)
    }

    @Provides
    fun provideGetFriendRequestListUseCase(
        repository: FriendRepository
    ): GetFriendRequestListUseCase {
        return GetFriendRequestListUseCase(repository)
    }

    @Provides
    fun provideSendFriendRequestUseCase(
        repository: FriendRepository
    ): SendFriendRequestUseCase {
        return SendFriendRequestUseCase(repository)
    }

    @Provides
    fun provideAcceptFriendRequestUseCase(
        repository: FriendRepository
    ): AcceptFriendRequestUseCase {
        return AcceptFriendRequestUseCase(repository)
    }

    @Provides
    fun provideRefuseFriendRequestUseCase(
        repository: FriendRepository
    ): RefuseFriendRequestUseCase {
        return RefuseFriendRequestUseCase(repository)
    }
}