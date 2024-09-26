package com.onmyway.domain.usecase.friend

import com.onmyway.domain.repository.FriendRepository
import com.onmyway.domain.request.friend.RefuseFriendRequestRequest
import kotlinx.coroutines.flow.flow

class RefuseFriendRequestUseCase(
    private val repository: FriendRepository
) {
    operator fun invoke(
        data: RefuseFriendRequestRequest
    ) = flow {
        val response = repository.refuseFriendRequest(data)
        emit(response)
    }
}