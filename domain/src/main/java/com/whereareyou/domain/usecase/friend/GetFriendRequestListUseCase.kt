package com.whereareyou.domain.usecase.friend

import com.whereareyou.domain.entity.apimessage.friend.GetFriendRequestListResponse
import com.whereareyou.domain.repository.FriendRepository
import com.whereareyou.domain.util.NetworkResult

class GetFriendRequestListUseCase(
    private val repository: FriendRepository
) {
    suspend operator fun invoke(
        token: String,
        memberId: String
    ): NetworkResult<GetFriendRequestListResponse> {
        return repository.getFriendRequestList(token, memberId)
    }
}