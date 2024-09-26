package com.onmyway.di.util

import android.content.Context
import com.onmyway.util.InputTextValidator
import com.onmyway.util.LocationUtil
import com.onmyway.util.NetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Provides
    @Singleton
    fun provideLocationUtil(
        @ApplicationContext context: Context
    ): LocationUtil {
        return LocationUtil(context)
    }

//    @Provides
//    @Singleton
//    fun provideTokenManager(
//        getAccessTokenUseCase: GetAccessTokenUseCase,
//        getRefreshTokenUseCase: GetRefreshTokenUseCase,
//        saveAccessTokenUseCase: SaveAccessTokenUseCase,
//        saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
//        reissueAccessTokenUseCase: ReissueAccessTokenUseCase,
//        @ApplicationContext context: Context
//    ): TokenManager {
//        return TokenManager(
//            getAccessTokenUseCase,
//            getRefreshTokenUseCase,
//            saveAccessTokenUseCase,
//            saveRefreshTokenUseCase,
//            reissueAccessTokenUseCase,
//            context
//        )
//    }

    @Provides
    @Singleton
    fun inputTextValidator(): InputTextValidator {
        return InputTextValidator()
    }

    @Provides
    @Singleton
    fun provideNetworkManager(
        @ApplicationContext context: Context
    ): NetworkManager {
        return NetworkManager(context)
    }
}