package com.whereareyounow.domain.usecase.signin

import com.whereareyounow.domain.entity.apimessage.signin.FindIdRequest
import com.whereareyounow.domain.entity.apimessage.signin.FindIdResponse
import com.whereareyounow.domain.repository.SignInRepository
import com.whereareyounow.domain.util.NetworkResult

class FindIdUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        body: FindIdRequest
    ): NetworkResult<FindIdResponse> {
        return repository.findId(body)
    }
}