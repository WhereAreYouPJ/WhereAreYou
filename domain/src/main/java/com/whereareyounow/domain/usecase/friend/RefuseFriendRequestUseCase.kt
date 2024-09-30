package com.whereareyounow.domain.usecase.friend

import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.request.friend.RefuseFriendRequestRequest
import kotlinx.coroutines.flow.flow

class RefuseFriendRequestUseCase(
    private val repository: FriendRepository
) {
    operator fun invoke(
        data: RefuseFriendRequestRequest
    ) = flow {
        val response = repository.refuseFriendRequest(data)
        emit(response)
    }
}