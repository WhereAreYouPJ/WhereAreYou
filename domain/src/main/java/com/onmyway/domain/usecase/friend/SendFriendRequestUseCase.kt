package com.onmyway.domain.usecase.friend

import com.onmyway.domain.repository.FriendRepository
import com.onmyway.domain.request.friend.SendFriendRequestRequest
import kotlinx.coroutines.flow.flow

class SendFriendRequestUseCase(
    private val repository: FriendRepository
) {
    operator fun invoke(
        data: SendFriendRequestRequest
    ) = flow {
        val response = repository.sendFriendRequest(data)
        emit(response)
    }
}