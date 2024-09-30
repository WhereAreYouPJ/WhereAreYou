package com.whereareyounow.domain.usecase.member

import com.whereareyounow.domain.repository.MemberRepository
import com.whereareyounow.domain.request.member.GetUserInfoByMemberSeqRequest
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