package com.whereareyounow.domain.usecase.member

import com.whereareyounow.domain.repository.MemberRepository
import com.whereareyounow.domain.request.member.GetUserInfoByMemberCodeRequest
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