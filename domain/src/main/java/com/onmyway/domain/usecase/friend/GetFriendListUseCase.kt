package com.onmyway.domain.usecase.friend

import com.onmyway.domain.repository.FriendRepository
import com.onmyway.domain.request.friend.GetFriendListRequest
import kotlinx.coroutines.flow.flow

class GetFriendListUseCase(
    private val repository: FriendRepository
) {
    operator fun invoke(
        data: GetFriendListRequest
    ) = flow {
        val response = repository.getFriendList(data)
        emit(response)
    }
}