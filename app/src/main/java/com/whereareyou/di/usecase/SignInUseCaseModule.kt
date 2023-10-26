package com.whereareyou.di.usecase

import com.whereareyou.domain.repository.SignInRepository
import com.whereareyou.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyou.domain.usecase.signin.SaveAccessTokenUseCase
import com.whereareyou.domain.usecase.signin.SignInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SignInUseCaseModule {

    @Provides
    fun provideSignInUseCase(
        repository: SignInRepository
    ): SignInUseCase {
        return SignInUseCase(repository)
    }

    @Provides
    fun provideSaveAccessTokenUseCase(
        repository: SignInRepository
    ): SaveAccessTokenUseCase {
        return SaveAccessTokenUseCase(repository)
    }

    @Provides
    fun provideGetAccessTokenUseCase(
        repository: SignInRepository
    ): GetAccessTokenUseCase {
        return GetAccessTokenUseCase(repository)
    }
}