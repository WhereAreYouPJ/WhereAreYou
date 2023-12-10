package com.whereareyounow.di.usecase

import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.usecase.friend.AcceptFriendRequestUseCase
import com.whereareyounow.domain.usecase.friend.GetFriendIdsListUseCase
import com.whereareyounow.domain.usecase.friend.GetFriendListUseCase
import com.whereareyounow.domain.usecase.friend.GetFriendRequestListUseCase
import com.whereareyounow.domain.usecase.friend.RefuseFriendRequestUseCase
import com.whereareyounow.domain.usecase.friend.SendFriendRequestUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FriendUseCaseModule {

    @Provides
    fun provideAcceptFriendRequest(
        repository: FriendRepository
    ): AcceptFriendRequestUseCase {
        return AcceptFriendRequestUseCase(repository)
    }

    @Provides
    fun provideGetFriendIdsListUseCase(
        repository: FriendRepository
    ): GetFriendIdsListUseCase {
        return GetFriendIdsListUseCase(repository)
    }

    @Provides
    fun provideGetFriendListUseCase(
        repository: FriendRepository
    ): GetFriendListUseCase {
        return GetFriendListUseCase(repository)
    }

    @Provides
    fun provideGetFriendRequestListUseCase(
        repository: FriendRepository
    ): GetFriendRequestListUseCase {
        return GetFriendRequestListUseCase(repository)
    }

    @Provides
    fun provideRefuseFriendRequestUseCase(
        repository: FriendRepository
    ): RefuseFriendRequestUseCase {
        return RefuseFriendRequestUseCase(repository)
    }

    @Provides
    fun provideSendFriendRequestUseCase(
        repository: FriendRepository
    ): SendFriendRequestUseCase {
        return SendFriendRequestUseCase(repository)
    }
}