package com.whereareyounow.di.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.whereareyounow.api.FCMApi
import com.whereareyounow.api.FeedApi
import com.whereareyounow.api.FriendApi
import com.whereareyounow.api.LocationApi
import com.whereareyounow.api.MemberApi
import com.whereareyounow.api.ScheduleApi
import com.whereareyounow.api.SearchLocationApi
import com.whereareyounow.datasource.SearchLocationDataSource
import com.whereareyounow.di.datastore.DataStoreModule
import com.whereareyounow.domain.repository.DataStoreRepository
import com.whereareyounow.domain.repository.FCMRepository
import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.repository.LocationRepository
import com.whereareyounow.domain.repository.MemberRepository
import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.repository.SearchLocationRepository
import com.whereareyounow.repository.DataStoreRepositoryImpl
import com.whereareyounow.repository.FCMRepositoryImpl
import com.whereareyounow.repository.FeedRepositoryImpl
import com.whereareyounow.repository.FriendRepositoryImpl
import com.whereareyounow.repository.LocationRepositoryImpl
import com.whereareyounow.repository.MemberRepositoryImpl
import com.whereareyounow.repository.ScheduleRepositoryImpl
import com.whereareyounow.repository.SearchLocationRepositoryImpl
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

    @Provides
    fun provideSearchLocationRepository(
        api: SearchLocationApi
    ): SearchLocationRepository {
        return SearchLocationRepositoryImpl(api)
    }
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

    @Provides
    fun provideFeedRepository(
        feedApi: FeedApi
    ): FeedRepository {
        return FeedRepositoryImpl(feedApi)
    }

    @Singleton
    @Provides
    fun provideRecentSearchDataStoreRepository(
        @DataStoreModule.DataStore dataStore: DataStore<Preferences>
    ): DataStoreRepository {
        return DataStoreRepositoryImpl(dataStore)
    }

    @Provides
    fun provideFeedRepository(
        feedApi: FeedApi
    ): FeedRepository {
        return FeedRepositoryImpl(feedApi)
    }

}