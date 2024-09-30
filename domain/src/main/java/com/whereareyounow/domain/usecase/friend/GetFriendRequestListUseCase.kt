package com.whereareyounow.domain.usecase.friend

import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.request.friend.GetFriendRequestListRequest
import kotlinx.coroutines.flow.flow

class GetFriendRequestListUseCase(
    private val repository: FriendRepository
) {
    operator fun invoke(
        data: GetFriendRequestListRequest
    ) = flow {
        val response = repository.getFriendRequestList(data)
        emit(response)
    }
}