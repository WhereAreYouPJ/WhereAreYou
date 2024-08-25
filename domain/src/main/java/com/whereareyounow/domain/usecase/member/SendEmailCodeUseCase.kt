package com.whereareyounow.domain.usecase.member

import com.whereareyounow.domain.repository.MemberRepository
import com.whereareyounow.domain.request.member.SendEmailCodeRequest
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