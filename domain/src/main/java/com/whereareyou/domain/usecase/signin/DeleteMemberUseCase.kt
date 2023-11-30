package com.whereareyou.domain.usecase.signin

import com.whereareyou.domain.entity.apimessage.signin.DeleteMemberRequest
import com.whereareyou.domain.repository.SignInRepository
import com.whereareyou.domain.util.NetworkResult

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