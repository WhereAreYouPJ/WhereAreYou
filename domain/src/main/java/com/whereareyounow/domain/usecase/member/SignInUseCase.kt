package com.whereareyounow.domain.usecase.member

import com.whereareyounow.domain.repository.MemberRepository
import com.whereareyounow.domain.request.member.SignInRequest
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