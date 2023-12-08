package com.whereareyounow.domain.usecase.signup

import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailRequest
import com.whereareyounow.domain.entity.apimessage.signup.AuthenticateEmailResponse
import com.whereareyounow.domain.repository.SignUpRepository
import com.whereareyounow.domain.util.NetworkResult

class AuthenticateEmailUseCase(
    private val repository: SignUpRepository
) {
    suspend operator fun invoke(
        body: AuthenticateEmailRequest
    ): NetworkResult<AuthenticateEmailResponse> {
        return repository.authenticateEmail(body)
    }
}