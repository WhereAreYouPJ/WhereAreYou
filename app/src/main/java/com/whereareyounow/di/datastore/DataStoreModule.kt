package com.whereareyounow.di.datastore

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.whereareyounow.globalvalue.DATA_STORE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DataStore

    @Singleton
    @Provides
    @DataStore
    fun provideDataStore(
        @ApplicationContext context: Context
    ): androidx.datastore.core.DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(DATA_STORE) }
        )
    }
}