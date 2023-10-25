package com.whereareyou.domain.usecase.signup

import com.whereareyou.domain.repository.SignUpRepository
import com.whereareyou.domain.util.NetworkResult

class AuthenticateEmailCodeUseCase(
    private val repository: SignUpRepository
) {
    suspend operator fun invoke(
        email: String,
        code: Int
    ): NetworkResult<String> {
        return repository.authenticateEmailCode(email, code)
    }
}