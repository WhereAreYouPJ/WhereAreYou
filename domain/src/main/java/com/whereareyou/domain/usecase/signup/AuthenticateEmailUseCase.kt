package com.whereareyou.domain.usecase.signup

import com.whereareyou.domain.entity.apimessage.signup.AuthenticateEmailRequest
import com.whereareyou.domain.entity.apimessage.signup.AuthenticateEmailResponse
import com.whereareyou.domain.repository.SignUpRepository
import com.whereareyou.domain.util.NetworkResult

class AuthenticateEmailUseCase(
    private val repository: SignUpRepository
) {
    suspend operator fun invoke(
        body: AuthenticateEmailRequest
    ): NetworkResult<AuthenticateEmailResponse> {
        return repository.authenticateEmail(body)
    }
}