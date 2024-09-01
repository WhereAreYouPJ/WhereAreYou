package com.whereareyounow.di.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.whereareyounow.api.FCMApi
import com.whereareyounow.api.FriendApi
import com.whereareyounow.api.LocationApi
import com.whereareyounow.api.MemberApi
import com.whereareyounow.api.ScheduleApi
import com.whereareyounow.datasource.SearchLocationDataSource
import com.whereareyounow.datasource.SharedPreferencesDataSource
import com.whereareyounow.domain.repository.FCMRepository
import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.repository.LocationRepository
import com.whereareyounow.domain.repository.MemberRepository
import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.repository.SearchLocationRepository
import com.whereareyounow.repository.FCMRepositoryImpl
import com.whereareyounow.repository.FriendRepositoryImpl
import com.whereareyounow.repository.LocationRepositoryImpl
import com.whereareyounow.repository.MemberRepositoryImpl
import com.whereareyounow.repository.ScheduleRepositoryImpl
import com.whereareyounow.repository.SearchLocationRepositoryImpl
import com.whereareyounow.repository.SharedPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideScheduleRepository(
        scheduleApi: ScheduleApi
    ): ScheduleRepository {
        return ScheduleRepositoryImpl(scheduleApi)
    }

    @Provides
    fun provideMemberRepository(
        memberApi: MemberApi
    ): MemberRepository {
        return MemberRepositoryImpl(memberApi)
    }

    @Provides
    fun provideFriendRepository(
        friendApi: FriendApi
    ): FriendRepository {
        return FriendRepositoryImpl(friendApi)
    }

    @Provides
    fun provideLocationRepository(
        locationApi: LocationApi
    ): LocationRepository {
        return LocationRepositoryImpl(locationApi)
    }

//    @Provides
//    fun provideSearchLocationRepository(
//        dataSource: SearchLocationDataSource
//    ): SearchLocationRepository {
//        return SearchLocationRepositoryImpl(dataSource)
//    }
//
//    @Provides
//    fun provideSharedPreferencesRepository(
//        dataSource: SharedPreferencesDataSource
//    ): SharedPreferencesRepository {
//        return SharedPreferencesRepository(dataSource)
//    }

    @Provides
    fun provideFcmRepository(
        fcmApi: FCMApi
    ): FCMRepository {
        return FCMRepositoryImpl(fcmApi)
    }
}