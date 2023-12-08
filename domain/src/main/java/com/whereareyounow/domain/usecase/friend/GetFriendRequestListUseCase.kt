package com.whereareyounow.domain.usecase.friend

import com.whereareyounow.domain.entity.apimessage.friend.GetFriendRequestListResponse
import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.util.NetworkResult

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