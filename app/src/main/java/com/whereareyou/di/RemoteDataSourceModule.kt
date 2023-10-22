package com.whereareyou.di

import com.whereareyou.api.SearchLocationApi
import com.whereareyou.api.WhereAreYouApi
import com.whereareyou.datasource.RemoteDataSource
import com.whereareyou.datasource.SearchLocationDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        api: WhereAreYouApi
    ): RemoteDataSource {
        return RemoteDataSource(api)
    }

    @Provides
    @Singleton
    fun provideSearchLocationDataSource(
        api: SearchLocationApi
    ): SearchLocationDataSource {
        return SearchLocationDataSource(api)
    }
}