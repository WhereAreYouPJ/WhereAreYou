package com.whereareyounow.di.usecase

import com.whereareyounow.domain.repository.HomeRepository
import com.whereareyounow.domain.usecase.home.GetHomeImageListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HomeUseCaseModule {

    @Provides
    fun provideGetHomeImageListUseCase(
        repository: HomeRepository
    ) = GetHomeImageListUseCase(repository)
}