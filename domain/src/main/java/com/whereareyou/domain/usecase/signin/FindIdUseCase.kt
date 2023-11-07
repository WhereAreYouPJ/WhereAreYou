package com.whereareyou.domain.usecase.signin

import com.whereareyou.domain.entity.apimessage.signin.FindIdRequest
import com.whereareyou.domain.entity.apimessage.signin.FindIdResponse
import com.whereareyou.domain.repository.SignInRepository
import com.whereareyou.domain.util.NetworkResult

class FindIdUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        body: FindIdRequest
    ): NetworkResult<FindIdResponse> {
        return repository.findId(body)
    }
}