package com.whereareyou.domain.usecase.signup

import com.whereareyou.domain.entity.apimessage.signup.AuthenticateEmailCodeRequest
import com.whereareyou.domain.entity.apimessage.signup.AuthenticateEmailCodeResponse
import com.whereareyou.domain.repository.SignUpRepository
import com.whereareyou.domain.util.NetworkResult

class AuthenticateEmailCodeUseCase(
    private val repository: SignUpRepository
) {
    suspend operator fun invoke(
        body: AuthenticateEmailCodeRequest
    ): NetworkResult<AuthenticateEmailCodeResponse> {
        return repository.authenticateEmailCode(body)
    }
}