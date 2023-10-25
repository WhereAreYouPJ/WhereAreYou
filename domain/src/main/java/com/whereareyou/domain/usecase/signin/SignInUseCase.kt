package com.whereareyou.domain.usecase.signin

import com.whereareyou.domain.entity.signin.SignInData
import com.whereareyou.domain.repository.SignInRepository
import com.whereareyou.domain.util.NetworkResult

class SignInUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        userId: String,
        password: String
    ): NetworkResult<SignInData> {
        return repository.signIn(userId, password)
    }
}