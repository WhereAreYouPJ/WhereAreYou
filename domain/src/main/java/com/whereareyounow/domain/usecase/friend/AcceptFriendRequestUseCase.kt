package com.whereareyounow.domain.usecase.friend

import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.request.friend.AcceptFriendRequestRequest
import kotlinx.coroutines.flow.flow

class AcceptFriendRequestUseCase(
    private val repository: FriendRepository
) {
    operator fun invoke(
        data: AcceptFriendRequestRequest
    ) = flow {
        val response = repository.acceptFriendRequest(data)
        emit(response)
    }
}