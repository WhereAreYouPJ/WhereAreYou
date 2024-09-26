package com.onmyway.di.usecase

import com.onmyway.domain.repository.LocationRepository
import com.onmyway.domain.usecase.location.GetFaboriteLocationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationUseCaseModule {

//    @Provides
//    fun provideGetUserLocationUseCase(
//        repository: LocationRepository
//    ): GetUserLocationUseCase {
//        return GetUserLocationUseCase(repository)
//    }
//
//    @Provides
//    fun provideSendUserLocationUseCase(
//        repository: LocationRepository
//    ): SendUserLocationUseCase {
//        return SendUserLocationUseCase(repository)
//    }
//
//    @Provides
//    fun provideGetLocationAddressUseCase(
//        repository: SearchLocationRepository
//    ): GetLocationAddressUseCase {
//        return GetLocationAddressUseCase(repository)
//    }

    @Singleton
    @Provides
    fun provideGetFaboriteLocationUseCase(
        repository: LocationRepository
    ): GetFaboriteLocationUseCase {
        return GetFaboriteLocationUseCase(repository)
    }
}
