package com.whereareyounow.domain.usecase.friend

import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.request.friend.DeleteFriendRequest
import kotlinx.coroutines.flow.flow

class DeleteFriendUseCase(
    private val repository: FriendRepository
) {
    operator fun invoke(
        data: DeleteFriendRequest
    ) = flow {
        val response = repository.deleteFriend(data)
        emit(response)
    }
}