package com.whereareyounow.domain.usecase.friend

import com.whereareyounow.domain.entity.apimessage.friend.RefuseFriendRequestRequest
import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.util.NetworkResult

class RefuseFriendRequestUseCase(
    private val repository: FriendRepository
) {
    suspend operator fun invoke(
        token: String,
        body: RefuseFriendRequestRequest
    ): NetworkResult<Unit> {
        return repository.refuseFriendRequest(token, body)
    }
}