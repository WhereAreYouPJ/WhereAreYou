package com.whereareyounow.domain.usecase.friend

import com.whereareyounow.domain.entity.apimessage.friend.SendFriendRequestRequest
import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.util.NetworkResult

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