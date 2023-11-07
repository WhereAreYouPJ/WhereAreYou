package com.whereareyou.domain.usecase.signin

import com.whereareyou.domain.entity.apimessage.signin.SignInRequest
import com.whereareyou.domain.entity.apimessage.signin.SignInResponse
import com.whereareyou.domain.entity.signin.SignInData
import com.whereareyou.domain.repository.SignInRepository
import com.whereareyou.domain.util.NetworkResult

class SignInUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        body: SignInRequest
    ): NetworkResult<SignInResponse> {
        return repository.signIn(body)
    }
}