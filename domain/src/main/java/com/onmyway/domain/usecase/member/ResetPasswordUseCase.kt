package com.onmyway.domain.usecase.member

import com.onmyway.domain.repository.MemberRepository
import com.onmyway.domain.request.member.ResetPasswordRequest
import kotlinx.coroutines.flow.flow

class ResetPasswordUseCase(
    private val repository: MemberRepository
) {
    operator fun invoke(
        data: ResetPasswordRequest
    ) = flow {
        val response = repository.resetPassword(data)
        emit(response)
    }
}