package com.whereareyounow.domain.usecase.signin

import com.whereareyounow.domain.entity.apimessage.signin.GetMemberDetailsByUserIdResponse
import com.whereareyounow.domain.repository.SignInRepository
import com.whereareyounow.domain.util.NetworkResult

class GetMemberDetailsByUserIdUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        token: String,
        memberId: String
    ): NetworkResult<GetMemberDetailsByUserIdResponse> {
        return repository.getMemberDetailsByUserId(token, memberId)
    }
}