package com.whereareyounow.di.usecase

import com.whereareyounow.domain.repository.DataStoreRepository
import com.whereareyounow.domain.usecase.datastore.ClearAuthDataStoreUseCase
import com.whereareyounow.domain.usecase.datastore.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.datastore.GetMemberCodeUseCase
import com.whereareyounow.domain.usecase.datastore.GetMemberSeqUseCase
import com.whereareyounow.domain.usecase.datastore.GetRefreshTokenUseCase
import com.whereareyounow.domain.usecase.datastore.SaveAccessTokenUseCase
import com.whereareyounow.domain.usecase.datastore.SaveMemberCodeUseCase
import com.whereareyounow.domain.usecase.datastore.SaveMemberSeqUseCase
import com.whereareyounow.domain.usecase.datastore.SaveRefreshTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreUseCaseModule {

    @Provides
    fun provideGetAccessTokenUseCase(
        repository: DataStoreRepository
    ): GetAccessTokenUseCase {
        return GetAccessTokenUseCase(repository)
    }

    @Provides
    fun provideGetRefreshTokenUseCase(
        repository: DataStoreRepository
    ): GetRefreshTokenUseCase {
        return GetRefreshTokenUseCase(repository)
    }

    @Provides
    fun provideSaveAccessTokenUseCase(
        repository: DataStoreRepository
    ): SaveAccessTokenUseCase {
        return SaveAccessTokenUseCase(repository)
    }

    @Provides
    fun provideSaveRefreshTokenUseCase(
        repository: DataStoreRepository
    ): SaveRefreshTokenUseCase {
        return SaveRefreshTokenUseCase(repository)
    }

    @Provides
    fun provideSaveMemberSeqUseCase(
        repository: DataStoreRepository
    ): SaveMemberSeqUseCase {
        return SaveMemberSeqUseCase(repository)
    }

    @Provides
    fun provideGetMemberSeqUseCase(
        repository: DataStoreRepository
    ): GetMemberSeqUseCase {
        return GetMemberSeqUseCase(repository)
    }

    @Provides
    fun provideSaveMemberCodeUseCase(
        repository: DataStoreRepository
    ): SaveMemberCodeUseCase {
        return SaveMemberCodeUseCase(repository)
    }

    @Provides
    fun provideGetMemberCodeUseCase(
        repository: DataStoreRepository
    ): GetMemberCodeUseCase {
        return GetMemberCodeUseCase(repository)
    }

    @Provides
    fun provideClearAuthDataStoreUseCase(
        repository: DataStoreRepository
    ): ClearAuthDataStoreUseCase {
        return ClearAuthDataStoreUseCase(repository)
    }
}