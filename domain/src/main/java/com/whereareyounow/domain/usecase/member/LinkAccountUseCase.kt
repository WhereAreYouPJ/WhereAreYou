package com.whereareyounow.domain.usecase.member

import com.whereareyounow.domain.repository.MemberRepository
import com.whereareyounow.domain.request.member.LinkAccountRequest
import kotlinx.coroutines.flow.flow

class LinkAccountUseCase(
    private val repository: MemberRepository
) {
    operator fun invoke(
        data: LinkAccountRequest
    ) = flow {
        val response = repository.linkAccount(data)
        emit(response)
    }
}