package com.whereareyounow.domain.usecase.member

import com.whereareyounow.domain.repository.MemberRepository
import com.whereareyounow.domain.request.member.SnsSignUpRequest
import kotlinx.coroutines.flow.flow

class SnsSignUpUseCase(
    private val repository: MemberRepository
) {
    operator fun invoke(
        data: SnsSignUpRequest
    ) = flow {
        val response = repository.snsSignUp(data)
        emit(response)
    }
}