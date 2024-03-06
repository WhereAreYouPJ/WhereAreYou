package com.whereareyounow.domain.usecase.friend

import com.whereareyounow.domain.entity.apimessage.friend.DeleteFriendRequest
import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.util.NetworkResult

class DeleteFriendUseCase(
    private val repository: FriendRepository
) {
    suspend operator fun invoke(
        token: String,
        body: DeleteFriendRequest
    ): NetworkResult<Unit> {
        return repository.deleteFriend(token, body)
    }
}