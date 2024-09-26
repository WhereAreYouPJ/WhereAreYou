package com.onmyway.domain.usecase.member

import com.onmyway.domain.repository.MemberRepository
import com.onmyway.domain.request.member.GetUserInfoByMemberCodeRequest
import kotlinx.coroutines.flow.flow

class GetUserInfoByMemberCodeUseCase(
    private val repository: MemberRepository
) {
    operator fun invoke(
        data: GetUserInfoByMemberCodeRequest
    ) = flow {
        val response = repository.getUserInfoByMemberCode(data)
        emit(response)
    }
}