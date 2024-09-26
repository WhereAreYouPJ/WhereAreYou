package com.onmyway.domain.usecase.member

import com.onmyway.domain.repository.MemberRepository
import com.onmyway.domain.request.member.VerifyEmailCodeRequest
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