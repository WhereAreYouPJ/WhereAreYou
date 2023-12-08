package com.whereareyounow.di.usecase

import com.whereareyounow.domain.repository.SignInRepository
import com.whereareyounow.domain.usecase.signin.DeleteMemberUseCase
import com.whereareyounow.domain.usecase.signin.FindIdUseCase
import com.whereareyounow.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberDetailsByUserIdUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberDetailsUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyounow.domain.usecase.signin.GetRefreshTokenUseCase
import com.whereareyounow.domain.usecase.signin.ModifyMyInfoUseCase
import com.whereareyounow.domain.usecase.signin.ReissueTokenUseCase
import com.whereareyounow.domain.usecase.signin.ResetPasswordUseCase
import com.whereareyounow.domain.usecase.signin.SaveAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.SaveMemberIdUseCase
import com.whereareyounow.domain.usecase.signin.SaveRefreshTokenUseCase
import com.whereareyounow.domain.usecase.signin.SignInUseCase
import com.whereareyounow.domain.usecase.signin.VerifyPasswordResetCodeUseCase
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

    @Provides
    fun provideSaveMemberIdUseCase(
        repository: SignInRepository
    ): SaveMemberIdUseCase {
        return SaveMemberIdUseCase(repository)
    }

    @Provides
    fun provideGetMemberIdUseCase(
        repository: SignInRepository
    ): GetMemberIdUseCase {
        return GetMemberIdUseCase(repository)
    }

    @Provides
    fun provideSaveRefreshTokenUseCase(
        repository: SignInRepository
    ): SaveRefreshTokenUseCase {
        return SaveRefreshTokenUseCase(repository)
    }

    @Provides
    fun provideGetRefreshTokenUseCase(
        repository: SignInRepository
    ): GetRefreshTokenUseCase {
        return GetRefreshTokenUseCase(repository)
    }

    @Provides
    fun provideDeleteMemberUseCase(
        repository: SignInRepository
    ): DeleteMemberUseCase {
        return DeleteMemberUseCase(repository)
    }

    @Provides
    fun provideFindIdUseCase(
        repository: SignInRepository
    ): FindIdUseCase {
        return FindIdUseCase(repository)
    }

    @Provides
    fun provideGetMemberDetailsUseCase(
        repository: SignInRepository
    ): GetMemberDetailsUseCase {
        return GetMemberDetailsUseCase(repository)
    }

    @Provides
    fun provideGetMemberDetailsByUserIdUseCase(
        repository: SignInRepository
    ): GetMemberDetailsByUserIdUseCase {
        return GetMemberDetailsByUserIdUseCase(repository)
    }

    @Provides
    fun provideModifyMyInfoUseCase(
        repository: SignInRepository
    ): ModifyMyInfoUseCase {
        return ModifyMyInfoUseCase(repository)
    }

    @Provides
    fun provideReissueTokenUseCase(
        repository: SignInRepository
    ): ReissueTokenUseCase {
        return ReissueTokenUseCase(repository)
    }

    @Provides
    fun provideResetPasswordUseCase(
        repository: SignInRepository
    ): ResetPasswordUseCase {
        return ResetPasswordUseCase(repository)
    }

    @Provides
    fun provideVerifyPasswordResetCodeUseCase(
        repository: SignInRepository
    ): VerifyPasswordResetCodeUseCase {
        return VerifyPasswordResetCodeUseCase(repository)
    }
}