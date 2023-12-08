package com.whereareyounow.domain.usecase.signin

import com.whereareyounow.domain.entity.apimessage.signin.VerifyPasswordResetCodeRequest
import com.whereareyounow.domain.entity.apimessage.signin.VerifyPasswordResetCodeResponse
import com.whereareyounow.domain.repository.SignInRepository
import com.whereareyounow.domain.util.NetworkResult

class VerifyPasswordResetCodeUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        body: VerifyPasswordResetCodeRequest
    ): NetworkResult<VerifyPasswordResetCodeResponse> {
        return repository.verifyPasswordResetCode(body)
    }
}