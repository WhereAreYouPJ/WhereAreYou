package com.whereareyou.domain.usecase.signin

import com.whereareyou.domain.entity.apimessage.signin.ResetPasswordRequest
import com.whereareyou.domain.repository.SignInRepository
import com.whereareyou.domain.util.NetworkResult

class ResetPasswordUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        body: ResetPasswordRequest
    ): NetworkResult<Nothing> {
        return repository.resetPassword(body)
    }
}