package com.whereareyounow.domain.usecase.friend

import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.request.friend.GetFriendListRequest
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