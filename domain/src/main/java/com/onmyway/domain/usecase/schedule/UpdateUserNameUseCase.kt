package com.onmyway.domain.usecase.schedule

import com.onmyway.domain.repository.MemberRepository
import com.onmyway.domain.request.member.UpdateUserNameRequest
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