package com.onmyway.di.usecase

import com.onmyway.domain.repository.MemberRepository
import com.onmyway.domain.usecase.member.CheckEmailDuplicateUseCase
import com.onmyway.domain.usecase.member.GetUserInfoByMemberCodeUseCase
import com.onmyway.domain.usecase.member.GetUserInfoByMemberSeqUseCase
import com.onmyway.domain.usecase.member.ResetPasswordUseCase
import com.onmyway.domain.usecase.member.SendEmailCodeUseCase
import com.onmyway.domain.usecase.member.SignInUseCase
import com.onmyway.domain.usecase.member.SignOutUseCase
import com.onmyway.domain.usecase.member.SignUpUseCase
import com.onmyway.domain.usecase.member.UpdateProfileImageUseCase
import com.onmyway.domain.usecase.member.VerifyEmailCodeUseCase
import com.onmyway.domain.usecase.member.VerifyPasswordResetCodeUseCase
import com.onmyway.domain.usecase.schedule.UpdateUserNameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MemberUseCaseModule {

    @Provides
    fun provideUpdateUserNameUseCase(
        repository: MemberRepository
    ): UpdateUserNameUseCase {
        return UpdateUserNameUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideUpdateProfileImageUseCase(
        repository: MemberRepository
    ): UpdateProfileImageUseCase {
        return UpdateProfileImageUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSignUpUseCase(
        repository: MemberRepository
    ): SignUpUseCase {
        return SignUpUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideResetPasswordUseCase(
        repository: MemberRepository
    ): ResetPasswordUseCase {
        return ResetPasswordUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSignOutUseCase(
        repository: MemberRepository
    ): SignOutUseCase {
        return SignOutUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSignInUseCase(
        repository: MemberRepository
    ): SignInUseCase {
        return SignInUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideVerifyEmailCodeUseCase(
        repository: MemberRepository
    ): VerifyEmailCodeUseCase {
        return VerifyEmailCodeUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideVerifyPasswordResetCodeUseCase(
        repository: MemberRepository
    ): VerifyPasswordResetCodeUseCase {
        return VerifyPasswordResetCodeUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideSendEmailCodeUseCase(
        repository: MemberRepository
    ): SendEmailCodeUseCase {
        return SendEmailCodeUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetUserInfoByMemberCodeUseCase(
        repository: MemberRepository
    ): GetUserInfoByMemberCodeUseCase {
        return GetUserInfoByMemberCodeUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetUserInfoByMemberSeqUseCase(
        repository: MemberRepository
    ): GetUserInfoByMemberSeqUseCase {
        return GetUserInfoByMemberSeqUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideCheckEmailDuplicateUseCase(
        repository: MemberRepository
    ): CheckEmailDuplicateUseCase {
        return CheckEmailDuplicateUseCase(repository)
    }
}