package com.whereareyounow.domain.usecase.signin

import com.whereareyounow.domain.entity.apimessage.signin.GetMemberDetailsResponse
import com.whereareyounow.domain.repository.SignInRepository
import com.whereareyounow.domain.util.NetworkResult

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