package com.whereareyou.domain.usecase.friend

import com.whereareyou.domain.entity.apimessage.friend.AcceptFriendRequestRequest
import com.whereareyou.domain.repository.FriendRepository
import com.whereareyou.domain.util.NetworkResult

class AcceptFriendRequestUseCase(
    private val repository: FriendRepository
) {
    suspend operator fun invoke(
        token: String,
        body: AcceptFriendRequestRequest
    ): NetworkResult<Unit> {
        return repository.acceptFriendRequest(token, body)
    }
}