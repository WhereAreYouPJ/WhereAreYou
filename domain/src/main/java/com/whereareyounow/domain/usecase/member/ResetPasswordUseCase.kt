package com.whereareyounow.domain.usecase.member

import com.whereareyounow.domain.repository.MemberRepository
import com.whereareyounow.domain.request.member.ResetPasswordRequest
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