package com.whereareyounow.di.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.whereareyounow.datasource.RemoteDataSource
import com.whereareyounow.datasource.SearchLocationDataSource
import com.whereareyounow.datasource.SharedPreferencesDataSource
import com.whereareyounow.domain.repository.FCMRepository
import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.repository.LocationRepository
import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.repository.SearchLocationRepository
import com.whereareyounow.domain.repository.SignInRepository
import com.whereareyounow.domain.repository.SignUpRepository
import com.whereareyounow.repository.FCMRepositoryImpl
import com.whereareyounow.repository.FriendRepositoryImpl
import com.whereareyounow.repository.LocationRepositoryImpl
import com.whereareyounow.repository.ScheduleRepositoryImpl
import com.whereareyounow.repository.SearchLocationRepositoryImpl
import com.whereareyounow.repository.SharedPreferencesRepository
import com.whereareyounow.repository.SignInRepositoryImpl
import com.whereareyounow.repository.SignUpRepositoryImpl
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

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

    @Provides
    fun provideSignInRepository(
        dataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): SignInRepository {
        return SignInRepositoryImpl(
            dataSource,
            context.dataStore
        )
    }

    @Provides
    fun provideFriendRepository(
        dataSource: RemoteDataSource
    ): FriendRepository {
        return FriendRepositoryImpl(dataSource)
    }

    @Provides
    fun provideLocationRepository(
        dataSource: RemoteDataSource
    ): LocationRepository {
        return LocationRepositoryImpl(dataSource)
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

    @Provides
    fun provideFcmRepository(
        dataSource: RemoteDataSource
    ): FCMRepository {
        return FCMRepositoryImpl(dataSource)
    }
}