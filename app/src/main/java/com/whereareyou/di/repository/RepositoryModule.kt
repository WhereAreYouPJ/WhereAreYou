package com.whereareyou.di.repository

import com.whereareyou.datasource.RemoteDataSource
import com.whereareyou.datasource.SearchLocationDataSource
import com.whereareyou.datasource.SharedPreferencesDataSource
import com.whereareyou.domain.repository.ScheduleRepository
import com.whereareyou.domain.repository.SearchLocationRepository
import com.whereareyou.domain.repository.SignInRepository
import com.whereareyou.domain.repository.SignUpRepository
import com.whereareyou.repository.ScheduleRepositoryImpl
import com.whereareyou.repository.SearchLocationRepositoryImpl
import com.whereareyou.repository.SharedPreferencesRepository
import com.whereareyou.repository.SignInRepositoryImpl
import com.whereareyou.repository.SignUpRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideScheduleRepository(
        dataSource: RemoteDataSource
    ): ScheduleRepository {
        return ScheduleRepositoryImpl(dataSource)
    }

    @Provides
    fun provideSignUpRepository(
        dataSource: RemoteDataSource
    ): SignUpRepository {
        return SignUpRepositoryImpl(dataSource)
    }

    @Provides
    fun provideSignInRepository(
        dataSource: RemoteDataSource
    ): SignInRepository {
        return SignInRepositoryImpl(dataSource)
    }

    @Provides
    fun provideSearchLocationRepository(
        dataSource: SearchLocationDataSource
    ): SearchLocationRepository {
        return SearchLocationRepositoryImpl(dataSource)
    }

    @Provides
    fun provideSharedPreferencesRepository(
        dataSource: SharedPreferencesDataSource
    ): SharedPreferencesRepository {
        return SharedPreferencesRepository(dataSource)
    }
}