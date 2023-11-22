package com.whereareyou.di.usecase

import com.whereareyou.domain.repository.FriendRepository
import com.whereareyou.domain.usecase.friend.AcceptFriendRequestUseCase
import com.whereareyou.domain.usecase.friend.GetFriendIdsListUseCase
import com.whereareyou.domain.usecase.friend.GetFriendListUseCase
import com.whereareyou.domain.usecase.friend.GetFriendRequestListUseCase
import com.whereareyou.domain.usecase.friend.RefuseFriendRequestUseCase
import com.whereareyou.domain.usecase.friend.SendFriendRequestUseCase
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