package com.onmyway.domain.usecase.member

import com.onmyway.domain.repository.MemberRepository
import com.onmyway.domain.request.member.SignOutRequest
import kotlinx.coroutines.flow.flow

class SignOutUseCase(
    private val repository: MemberRepository
) {
    operator fun invoke(
        data: SignOutRequest
    ) = flow {
        val response = repository.signOut(data)
        emit(response)
    }
}