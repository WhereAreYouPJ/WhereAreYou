package com.whereareyou.domain.usecase.signup

import com.whereareyou.domain.entity.apimessage.signup.SignUpRequest
import com.whereareyou.domain.entity.apimessage.signup.SignUpResponse
import com.whereareyou.domain.repository.SignUpRepository
import com.whereareyou.domain.util.NetworkResult

class SignUpUseCase(
    private val repository: SignUpRepository
) {
    suspend operator fun invoke(
        body: SignUpRequest
    ): NetworkResult<SignUpResponse> {
        return repository.signUp(body)
    }
}