package com.whereareyou.domain.usecase.signin

import com.whereareyou.domain.entity.apimessage.signin.VerifyPasswordResetCodeRequest
import com.whereareyou.domain.entity.apimessage.signin.VerifyPasswordResetCodeResponse
import com.whereareyou.domain.repository.SignInRepository
import com.whereareyou.domain.util.NetworkResult

class VerifyPasswordResetCodeUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        body: VerifyPasswordResetCodeRequest
    ): NetworkResult<VerifyPasswordResetCodeResponse> {
        return repository.verifyPasswordResetCode(body)
    }
}