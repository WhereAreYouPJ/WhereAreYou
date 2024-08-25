package com.whereareyounow.domain.usecase.member

import com.whereareyounow.domain.repository.MemberRepository
import com.whereareyounow.domain.request.member.SignOutRequest
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