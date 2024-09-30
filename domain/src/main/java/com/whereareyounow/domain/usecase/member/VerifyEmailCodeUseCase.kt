package com.whereareyounow.domain.usecase.member

import com.whereareyounow.domain.repository.MemberRepository
import com.whereareyounow.domain.request.member.VerifyEmailCodeRequest
import kotlinx.coroutines.flow.flow

class VerifyEmailCodeUseCase(
    private val repository: MemberRepository
) {
    operator fun invoke(
        data: VerifyEmailCodeRequest
    ) = flow {
        val response = repository.verifyEmailCode(data)
        emit(response)
    }
}