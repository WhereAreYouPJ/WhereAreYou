package com.whereareyou.di.usecase

import com.whereareyou.domain.repository.SignUpRepository
import com.whereareyou.domain.usecase.signup.AuthenticateEmailCodeUseCase
import com.whereareyou.domain.usecase.signup.AuthenticateEmailUseCase
import com.whereareyou.domain.usecase.signup.CheckEmailDuplicateUseCase
import com.whereareyou.domain.usecase.signup.CheckIdDuplicateUseCase
import com.whereareyou.domain.usecase.signup.SignUpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SignUpUseCaseModule {

    @Provides
    fun provideAuthenticateEmailCodeUseCase(
        repository: SignUpRepository
    ): AuthenticateEmailCodeUseCase {
        return AuthenticateEmailCodeUseCase(repository)
    }

    @Provides
    fun provideAuthenticateEmailUseCase(
        repository: SignUpRepository
    ): AuthenticateEmailUseCase {
        return AuthenticateEmailUseCase(repository)
    }

    @Provides
    fun provideCheckEmailDuplicateUseCase(
        repository: SignUpRepository
    ): CheckEmailDuplicateUseCase {
        return CheckEmailDuplicateUseCase(repository)
    }

    @Provides
    fun provideCheckIdDuplicateUseCase(
        repository: SignUpRepository
    ): CheckIdDuplicateUseCase {
        return CheckIdDuplicateUseCase(repository)
    }

    @Provides
    fun provideSignUpUseCase(
        repository: SignUpRepository
    ): SignUpUseCase {
        return SignUpUseCase(repository)
    }
}