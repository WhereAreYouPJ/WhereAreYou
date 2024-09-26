package com.onmyway.domain.usecase.member

import com.onmyway.domain.repository.MemberRepository
import com.onmyway.domain.request.member.SignUpRequest
import kotlinx.coroutines.flow.flow

class SignUpUseCase(
    private val repository: MemberRepository
) {
    operator fun invoke(
        data: SignUpRequest
    ) = flow {
        val response = repository.signUp(data)
        emit(response)
    }
}