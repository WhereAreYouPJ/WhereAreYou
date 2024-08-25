package com.whereareyounow.domain.usecase.member

import com.whereareyounow.domain.repository.MemberRepository
import com.whereareyounow.domain.request.member.SignUpRequest
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