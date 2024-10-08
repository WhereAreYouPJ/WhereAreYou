package com.whereareyounow.domain.usecase.member

import com.whereareyounow.domain.repository.MemberRepository
import com.whereareyounow.domain.request.member.CheckEmailDuplicateRequest
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