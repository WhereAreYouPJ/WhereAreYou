package com.whereareyou.di.usecase

import com.whereareyou.domain.repository.LocationRepository
import com.whereareyou.domain.repository.SearchLocationRepository
import com.whereareyou.domain.usecase.location.GetLocationAddressUseCase
import com.whereareyou.domain.usecase.location.GetUserLocationUseCase
import com.whereareyou.domain.usecase.location.SendUserLocationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocationUseCaseModule {

    @Provides
    fun provideGetUserLocationUseCase(
        repository: LocationRepository
    ): GetUserLocationUseCase {
        return GetUserLocationUseCase(repository)
    }

    @Provides
    fun provideSendUserLocationUseCase(
        repository: LocationRepository
    ): SendUserLocationUseCase {
        return SendUserLocationUseCase(repository)
    }

    @Provides
    fun provideGetLocationAddressUseCase(
        repository: SearchLocationRepository
    ): GetLocationAddressUseCase {
        return GetLocationAddressUseCase(repository)
    }
}