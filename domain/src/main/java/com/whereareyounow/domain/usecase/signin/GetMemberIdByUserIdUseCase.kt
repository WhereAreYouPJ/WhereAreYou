package com.whereareyounow.domain.usecase.signin

import com.whereareyounow.domain.entity.apimessage.signin.GetMemberIdByUserIdResponse
import com.whereareyounow.domain.repository.SignInRepository
import com.whereareyounow.domain.util.NetworkResult

class GetMemberIdByUserIdUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        token: String,
        memberId: String
    ): NetworkResult<GetMemberIdByUserIdResponse> {
        return repository.getMemberIdByUserId(token, memberId)
    }
}