package com.whereareyounow.di.util

import android.content.Context
import com.whereareyounow.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.GetRefreshTokenUseCase
import com.whereareyounow.domain.usecase.signin.ReissueTokenUseCase
import com.whereareyounow.domain.usecase.signin.SaveAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.SaveRefreshTokenUseCase
import com.whereareyounow.util.InputTextValidator
import com.whereareyounow.util.LocationUtil
import com.whereareyounow.util.TokenManager
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

    @Provides
    @Singleton
    fun provideTokenManager(
        getAccessTokenUseCase: GetAccessTokenUseCase,
        getRefreshTokenUseCase: GetRefreshTokenUseCase,
        saveAccessTokenUseCase: SaveAccessTokenUseCase,
        saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
        reissueTokenUseCase: ReissueTokenUseCase,
        @ApplicationContext context: Context
    ): TokenManager {
        return TokenManager(
            getAccessTokenUseCase,
            getRefreshTokenUseCase,
            saveAccessTokenUseCase,
            saveRefreshTokenUseCase,
            reissueTokenUseCase,
            context
        )
    }

    @Provides
    @Singleton
    fun inputTextValidator(): InputTextValidator {
        return InputTextValidator()
    }
}