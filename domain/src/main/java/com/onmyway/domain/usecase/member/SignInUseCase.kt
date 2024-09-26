package com.onmyway.domain.usecase.member

import com.onmyway.domain.repository.MemberRepository
import com.onmyway.domain.request.member.SignInRequest
import kotlinx.coroutines.flow.flow

class SignInUseCase(
    private val repository: MemberRepository
) {
    operator fun invoke(
        data: SignInRequest
    ) = flow {
        val response = repository.signIn(data)
        emit(response)
    }
}