package com.onmyway.di.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.onmyway.api.FCMApi
import com.onmyway.api.FriendApi
import com.onmyway.api.LocationApi
import com.onmyway.api.MemberApi
import com.onmyway.api.ScheduleApi
import com.onmyway.di.datastore.DataStoreModule
import com.onmyway.domain.repository.DataStoreRepository
import com.onmyway.domain.repository.FCMRepository
import com.onmyway.domain.repository.FriendRepository
import com.onmyway.domain.repository.LocationRepository
import com.onmyway.domain.repository.MemberRepository
import com.onmyway.domain.repository.ScheduleRepository
import com.onmyway.repository.DataStoreRepositoryImpl
import com.onmyway.repository.FCMRepositoryImpl
import com.onmyway.repository.FriendRepositoryImpl
import com.onmyway.repository.LocationRepositoryImpl
import com.onmyway.repository.MemberRepositoryImpl
import com.onmyway.repository.ScheduleRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

    @Singleton
    @Provides
    fun provideRecentSearchDataStoreRepository(
        @DataStoreModule.DataStore dataStore: DataStore<Preferences>
    ): DataStoreRepository {
        return DataStoreRepositoryImpl(dataStore)
    }
}