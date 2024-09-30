package com.whereareyounow.di.usecase

import com.whereareyounow.domain.repository.DataStoreRepository
import com.whereareyounow.domain.usecase.datastore.ClearAuthDataStoreUseCase
import com.whereareyounow.domain.usecase.datastore.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.datastore.GetRefreshTokenUseCase
import com.whereareyounow.domain.usecase.datastore.SaveAccessTokenUseCase
import com.whereareyounow.domain.usecase.datastore.SaveRefreshTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreUseCaseModule {

    @Singleton
    @Provides
    fun provideGetAccessTokenUseCase(
        repository: DataStoreRepository
    ): GetAccessTokenUseCase {
        return GetAccessTokenUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetRefreshTokenUseCase(
        repository: DataStoreRepository
    ): GetRefreshTokenUseCase {
        return GetRefreshTokenUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSaveAccessTokenUseCase(
        repository: DataStoreRepository
    ): SaveAccessTokenUseCase {
        return SaveAccessTokenUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSaveRefreshTokenUseCase(
        repository: DataStoreRepository
    ): SaveRefreshTokenUseCase {
        return SaveRefreshTokenUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideClearAuthDataStoreUseCase(
        repository: DataStoreRepository
    ): ClearAuthDataStoreUseCase {
        return ClearAuthDataStoreUseCase(repository)
    }
}