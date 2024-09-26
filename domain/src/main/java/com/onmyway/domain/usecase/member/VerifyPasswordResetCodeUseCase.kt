package com.onmyway.domain.usecase.member

import com.onmyway.domain.repository.MemberRepository
import com.onmyway.domain.request.member.VerifyPasswordResetCodeRequest
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