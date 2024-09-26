package com.onmyway.domain.usecase.member

import com.onmyway.domain.repository.MemberRepository
import com.onmyway.domain.request.member.GetUserInfoByMemberSeqRequest
import kotlinx.coroutines.flow.flow

class GetUserInfoByMemberSeqUseCase(
    private val repository: MemberRepository
) {
    operator fun invoke(
        data: GetUserInfoByMemberSeqRequest
    ) = flow {
        val response = repository.getUserInfoByMemberSeq(data)
        emit(response)
    }
}