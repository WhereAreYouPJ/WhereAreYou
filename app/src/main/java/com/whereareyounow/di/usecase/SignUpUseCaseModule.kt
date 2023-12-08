package com.whereareyounow.di.usecase

import com.whereareyounow.domain.repository.SignUpRepository
import com.whereareyounow.domain.usecase.signup.AuthenticateEmailCodeUseCase
import com.whereareyounow.domain.usecase.signup.AuthenticateEmailUseCase
import com.whereareyounow.domain.usecase.signup.CheckEmailDuplicateUseCase
import com.whereareyounow.domain.usecase.signup.CheckIdDuplicateUseCase
import com.whereareyounow.domain.usecase.signup.SignUpUseCase
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