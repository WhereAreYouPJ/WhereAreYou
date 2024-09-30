package com.whereareyounow.domain.usecase.friend

import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.request.friend.SendFriendRequestRequest
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