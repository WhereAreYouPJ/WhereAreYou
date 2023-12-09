package com.whereareyounow.domain.usecase.signup

import com.whereareyounow.domain.entity.apimessage.signup.SignUpRequest
import com.whereareyounow.domain.repository.SignUpRepository
import com.whereareyounow.domain.util.NetworkResult

class SignUpUseCase(
    private val repository: SignUpRepository
) {
    suspend operator fun invoke(
        body: SignUpRequest
    ): NetworkResult<Unit> {
        return repository.signUp(body)
    }
}