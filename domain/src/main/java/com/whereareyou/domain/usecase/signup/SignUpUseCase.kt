package com.whereareyou.domain.usecase.signup

import com.whereareyou.domain.repository.SignUpRepository
import com.whereareyou.domain.util.NetworkResult

class SignUpUseCase(
    private val repository: SignUpRepository
) {
    suspend operator fun invoke(
        userName: String,
        userId: String,
        password: String,
        email: String
    ): NetworkResult<String> {
        return repository.signUp(userName, userId, password, email)
    }
}