package com.onmyway.domain.usecase.friend

import com.onmyway.domain.repository.FriendRepository
import com.onmyway.domain.request.friend.GetFriendRequestListRequest
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