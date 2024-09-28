package com.whereareyounow.di.usecase

import com.whereareyounow.domain.repository.LocationRepository
import com.whereareyounow.domain.usecase.location.DeleteFavoriteLocationUsecase
import com.whereareyounow.domain.usecase.location.GetFavoriteLocationUseCase
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
    fun provideGetFavoriteLocationUseCase(
        repository: LocationRepository
    ): GetFavoriteLocationUseCase {
        return GetFavoriteLocationUseCase(repository)
    }

    @Provides
    fun provideDeleteFavoriteLocationUsecase(
        repository: LocationRepository
    ): DeleteFavoriteLocationUsecase {
        return DeleteFavoriteLocationUsecase(repository)
    }
}
