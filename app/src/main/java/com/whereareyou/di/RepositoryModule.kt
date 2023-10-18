package com.whereareyou.di

import com.whereareyou.datasource.RemoteDataSource
import com.whereareyou.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideRemoteRepository(
        remoteDataSource: RemoteDataSource
    ): RemoteRepository {
        return RemoteRepository(remoteDataSource)
    }
}