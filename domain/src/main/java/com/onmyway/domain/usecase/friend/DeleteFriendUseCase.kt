package com.onmyway.domain.usecase.friend

import com.onmyway.domain.repository.FriendRepository
import com.onmyway.domain.request.friend.DeleteFriendRequest
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