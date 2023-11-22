package com.whereareyou.domain.usecase.friend

import com.whereareyou.domain.entity.apimessage.friend.RefuseFriendRequestRequest
import com.whereareyou.domain.repository.FriendRepository
import com.whereareyou.domain.util.NetworkResult

class RefuseFriendRequestUseCase(
    private val repository: FriendRepository
) {
    suspend operator fun invoke(
        token: String,
        body: RefuseFriendRequestRequest
    ): NetworkResult<Nothing> {
        return repository.refuseFriendRequest(token, body)
    }
}