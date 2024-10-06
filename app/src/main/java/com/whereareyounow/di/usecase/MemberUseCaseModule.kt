package com.whereareyounow.di.usecase

import com.whereareyounow.domain.repository.MemberRepository
import com.whereareyounow.domain.usecase.member.CheckEmailDuplicateUseCase
import com.whereareyounow.domain.usecase.member.GetUserInfoByMemberCodeUseCase
import com.whereareyounow.domain.usecase.member.GetUserInfoByMemberSeqUseCase
import com.whereareyounow.domain.usecase.member.LinkAccountUseCase
import com.whereareyounow.domain.usecase.member.ResetPasswordUseCase
import com.whereareyounow.domain.usecase.member.SendEmailCodeUseCase
import com.whereareyounow.domain.usecase.member.SignInUseCase
import com.whereareyounow.domain.usecase.member.SignOutUseCase
import com.whereareyounow.domain.usecase.member.SignUpUseCase
import com.whereareyounow.domain.usecase.member.SnsSignUpUseCase
import com.whereareyounow.domain.usecase.member.UpdateProfileImageUseCase
import com.whereareyounow.domain.usecase.member.UpdateUserNameUseCase
import com.whereareyounow.domain.usecase.member.VerifyEmailCodeUseCase
import com.whereareyounow.domain.usecase.member.VerifyPasswordResetCodeUseCase
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

    @Provides
    fun provideUpdateProfileImageUseCase(
        repository: MemberRepository
    ): UpdateProfileImageUseCase {
        return UpdateProfileImageUseCase(repository)
    }

    @Provides
    fun provideSignUpUseCase(
        repository: MemberRepository
    ): SignUpUseCase {
        return SignUpUseCase(repository)
    }

    @Provides
    fun provideSnsSignUpUseCase(
        repository: MemberRepository
    ) = SnsSignUpUseCase(repository)

    @Provides
    fun provideResetPasswordUseCase(
        repository: MemberRepository
    ): ResetPasswordUseCase {
        return ResetPasswordUseCase(repository)
    }

    @Provides
    fun provideSignOutUseCase(
        repository: MemberRepository
    ): SignOutUseCase {
        return SignOutUseCase(repository)
    }

    @Provides
    fun provideSignInUseCase(
        repository: MemberRepository
    ): SignInUseCase {
        return SignInUseCase(repository)
    }

    @Provides
    fun provideLinkAccountUseCase(
        repository: MemberRepository
    ) = LinkAccountUseCase(repository)

    @Provides
    fun provideVerifyEmailCodeUseCase(
        repository: MemberRepository
    ): VerifyEmailCodeUseCase {
        return VerifyEmailCodeUseCase(repository)
    }

    @Provides
    fun provideVerifyPasswordResetCodeUseCase(
        repository: MemberRepository
    ): VerifyPasswordResetCodeUseCase {
        return VerifyPasswordResetCodeUseCase(repository)
    }

    @Provides
    fun provideSendEmailCodeUseCase(
        repository: MemberRepository
    ): SendEmailCodeUseCase {
        return SendEmailCodeUseCase(repository)
    }

    @Provides
    fun provideGetUserInfoByMemberCodeUseCase(
        repository: MemberRepository
    ): GetUserInfoByMemberCodeUseCase {
        return GetUserInfoByMemberCodeUseCase(repository)
    }

    @Provides
    fun provideGetUserInfoByMemberSeqUseCase(
        repository: MemberRepository
    ): GetUserInfoByMemberSeqUseCase {
        return GetUserInfoByMemberSeqUseCase(repository)
    }

    @Provides
    fun provideCheckEmailDuplicateUseCase(
        repository: MemberRepository
    ): CheckEmailDuplicateUseCase {
        return CheckEmailDuplicateUseCase(repository)
    }
}