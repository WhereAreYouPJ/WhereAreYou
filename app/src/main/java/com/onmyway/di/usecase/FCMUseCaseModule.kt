package com.onmyway.di.usecase

import com.onmyway.domain.repository.FCMRepository
import com.onmyway.domain.usecase.fcm.DeleteFCMTokenUseCase
import com.onmyway.domain.usecase.fcm.UpdateFCMTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FCMUseCaseModule {

    @Provides
    fun provideUpdateFCMTokenUseCase(
        repository: FCMRepository
    ): UpdateFCMTokenUseCase {
        return UpdateFCMTokenUseCase(repository)
    }

    @Provides
    fun provideDeleteFCMTokenUseCase(
        repository: FCMRepository
    ): DeleteFCMTokenUseCase {
        return DeleteFCMTokenUseCase(repository)
    }
}