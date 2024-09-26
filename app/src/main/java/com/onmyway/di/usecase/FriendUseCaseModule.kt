package com.onmyway.di.usecase

import com.onmyway.domain.repository.FriendRepository
import com.onmyway.domain.usecase.friend.AcceptFriendRequestUseCase
import com.onmyway.domain.usecase.friend.DeleteFriendUseCase
import com.onmyway.domain.usecase.friend.GetFriendListUseCase
import com.onmyway.domain.usecase.friend.GetFriendRequestListUseCase
import com.onmyway.domain.usecase.friend.RefuseFriendRequestUseCase
import com.onmyway.domain.usecase.friend.SendFriendRequestUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FriendUseCaseModule {

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