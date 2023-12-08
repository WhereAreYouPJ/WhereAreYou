package com.whereareyounow.domain.usecase.signin

import com.whereareyounow.domain.entity.apimessage.signin.DeleteMemberRequest
import com.whereareyounow.domain.repository.SignInRepository
import com.whereareyounow.domain.util.NetworkResult

class DeleteMemberUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        token: String,
        body: DeleteMemberRequest
    ): NetworkResult<Unit> {
        return repository.deleteMember(token, body)
    }
}