package com.whereareyounow.di.usecase

import com.whereareyounow.domain.repository.LocationRepository
import com.whereareyounow.domain.repository.SearchLocationRepository
import com.whereareyounow.domain.usecase.location.GetLocationAddressUseCase
import com.whereareyounow.domain.usecase.location.GetUserLocationUseCase
import com.whereareyounow.domain.usecase.location.SendUserLocationUseCase
import dagger.Binds
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
