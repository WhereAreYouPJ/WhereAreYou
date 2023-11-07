package com.whereareyou.domain.usecase.signin

import com.whereareyou.domain.entity.apimessage.signin.GetMemberDetailsResponse
import com.whereareyou.domain.repository.SignInRepository
import com.whereareyou.domain.util.NetworkResult

class GetMemberDetailsUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        token: String,
        memberId: String
    ): NetworkResult<GetMemberDetailsResponse> {
        return repository.getMemberDetails(token, memberId)
    }
}