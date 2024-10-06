package com.whereareyounow.domain.usecase.member

import com.whereareyounow.domain.repository.MemberRepository
import com.whereareyounow.domain.request.member.UpdateUserNameRequest
import kotlinx.coroutines.flow.flow

class UpdateUserNameUseCase(
    private val repository: MemberRepository
) {
    operator fun invoke(
        data: UpdateUserNameRequest
    ) = flow {
        val response = repository.updateUserName(data)
        emit(response)
    }
}