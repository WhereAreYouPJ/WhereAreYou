package com.whereareyounow.domain.usecase.member

import com.whereareyounow.domain.repository.MemberRepository
import com.whereareyounow.domain.request.member.VerifyPasswordResetCodeRequest
import kotlinx.coroutines.flow.flow

class VerifyPasswordResetCodeUseCase(
    private val repository: MemberRepository
) {
    operator fun invoke(
        data: VerifyPasswordResetCodeRequest
    ) = flow {
        val response = repository.verifyPasswordResetCode(data)
        emit(response)
    }
}