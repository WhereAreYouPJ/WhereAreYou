package com.whereareyounow.di.datasource

import android.content.Context
import com.whereareyounow.api.FriendApi
import com.whereareyounow.api.LocationApi
import com.whereareyounow.api.SearchLocationApi
import com.whereareyounow.api.ScheduleApi
import com.whereareyounow.api.SignInApi
import com.whereareyounow.api.SignUpApi
import com.whereareyounow.datasource.RemoteDataSource
import com.whereareyounow.datasource.SearchLocationDataSource
import com.whereareyounow.datasource.SharedPreferencesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        scheduleApi: ScheduleApi,
        signUpApi: SignUpApi,
        signInApi: SignInApi,
        friendApi: FriendApi,
//        groupApi: GroupApi,
        locationApi: LocationApi
    ): RemoteDataSource {
        return RemoteDataSource(scheduleApi, signUpApi, signInApi, friendApi, locationApi)
    }

    @Provides
    @Singleton
    fun provideSearchLocationDataSource(
        api: SearchLocationApi
    ): SearchLocationDataSource {
        return SearchLocationDataSource(api)
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesDataSource(
        @ApplicationContext context: Context
    ): SharedPreferencesDataSource {
        return SharedPreferencesDataSource(context)
    }
}