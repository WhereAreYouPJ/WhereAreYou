package com.whereareyounow.domain.usecase.signin

import androidx.annotation.Keep
import com.whereareyounow.domain.entity.apimessage.signin.SignInRequest
import com.whereareyounow.domain.entity.apimessage.signin.SignInResponse
import com.whereareyounow.domain.repository.SignInRepository
import com.whereareyounow.domain.util.NetworkResult

class SignInUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        body: SignInRequest
    ): NetworkResult<SignInResponse> {
        return repository.signIn(body)
    }
}