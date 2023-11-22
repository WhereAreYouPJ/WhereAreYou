package com.whereareyou.di.util

import android.content.Context
import com.whereareyou.util.LocationUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    fun provideLocationUtil(
        @ApplicationContext context: Context
    ): LocationUtil {
        return LocationUtil(context)
    }
}