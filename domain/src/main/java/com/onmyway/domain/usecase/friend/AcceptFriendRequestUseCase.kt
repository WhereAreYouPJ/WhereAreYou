package com.onmyway.domain.usecase.friend

import com.onmyway.domain.repository.FriendRepository
import com.onmyway.domain.request.friend.AcceptFriendRequestRequest
import kotlinx.coroutines.flow.flow

class AcceptFriendRequestUseCase(
    private val repository: FriendRepository
) {
    operator fun invoke(
        data: AcceptFriendRequestRequest
    ) = flow {
        val response = repository.acceptFriendRequest(data)
        emit(response)
    }
}