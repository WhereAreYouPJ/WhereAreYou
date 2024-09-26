package com.onmyway.domain.usecase.member

import com.onmyway.domain.repository.MemberRepository
import com.onmyway.domain.request.member.SendEmailCodeRequest
import kotlinx.coroutines.flow.flow

class SendEmailCodeUseCase(
    private val repository: MemberRepository
) {
    operator fun invoke(
        data: SendEmailCodeRequest
    ) = flow {
        val response = repository.sendEmailCode(data)
        emit(response)
    }
}