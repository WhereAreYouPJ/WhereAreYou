package com.whereareyounow.domain.usecase.signin

import com.whereareyounow.domain.entity.apimessage.signin.ResetPasswordRequest
import com.whereareyounow.domain.repository.SignInRepository
import com.whereareyounow.domain.util.NetworkResult

class ResetPasswordUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        body: ResetPasswordRequest
    ): NetworkResult<Unit> {
        return repository.resetPassword(body)
    }
}