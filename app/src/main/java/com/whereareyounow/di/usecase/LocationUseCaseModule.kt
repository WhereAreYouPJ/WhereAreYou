package com.whereareyounow.di.usecase

import com.whereareyounow.domain.repository.LocationRepository
import com.whereareyounow.domain.repository.SearchLocationRepository
import com.whereareyounow.domain.usecase.location.GetFaboriteLocationUseCase
import com.whereareyounow.domain.usecase.location.SearchLocationAddressUseCase
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
    @Provides
    fun provideSearchLocationAddressUseCase(
        repository: SearchLocationRepository
    ): SearchLocationAddressUseCase {
        return SearchLocationAddressUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetFaboriteLocationUseCase(
        repository: LocationRepository
    ): GetFaboriteLocationUseCase {
        return GetFaboriteLocationUseCase(repository)
    }
}
