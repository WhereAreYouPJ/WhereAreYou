package com.onmyway.domain.usecase.member

import com.onmyway.domain.repository.MemberRepository
import com.onmyway.domain.request.member.CheckEmailDuplicateRequest
import kotlinx.coroutines.flow.flow

class CheckEmailDuplicateUseCase(
    private val repository: MemberRepository
) {
    operator fun invoke(
        data: CheckEmailDuplicateRequest
    ) = flow {
        val response = repository.checkEmailDuplicate(data)
        emit(response)
    }
}