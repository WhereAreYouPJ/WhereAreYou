package com.whereareyou.di.datasource

import android.content.Context
import com.whereareyou.api.FriendApi
import com.whereareyou.api.GroupApi
import com.whereareyou.api.LocationApi
import com.whereareyou.api.SearchLocationApi
import com.whereareyou.api.ScheduleApi
import com.whereareyou.api.SignInApi
import com.whereareyou.api.SignUpApi
import com.whereareyou.datasource.RemoteDataSource
import com.whereareyou.datasource.SearchLocationDataSource
import com.whereareyou.datasource.SharedPreferencesDataSource
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
//        friendApi: FriendApi,
//        groupApi: GroupApi,
        locationApi: LocationApi
    ): RemoteDataSource {
        return RemoteDataSource(scheduleApi, signUpApi, signInApi, locationApi)
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