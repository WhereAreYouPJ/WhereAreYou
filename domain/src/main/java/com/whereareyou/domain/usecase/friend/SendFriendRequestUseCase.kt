package com.whereareyou.domain.usecase.friend

import com.whereareyou.domain.entity.apimessage.friend.SendFriendRequestRequest
import com.whereareyou.domain.repository.FriendRepository
import com.whereareyou.domain.util.NetworkResult

class SendFriendRequestUseCase(
    private val repository: FriendRepository
) {
    suspend operator fun invoke(
        token: String,
        body: SendFriendRequestRequest
    ): NetworkResult<Unit> {
        return repository.sendFriendRequest(token, body)
    }
}