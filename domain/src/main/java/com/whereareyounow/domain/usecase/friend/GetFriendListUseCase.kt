package com.whereareyounow.domain.usecase.friend

import com.whereareyounow.domain.entity.apimessage.friend.GetFriendListRequest
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendListResponse
import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.util.NetworkResult

class GetFriendListUseCase(
    private val repository: FriendRepository
) {
    suspend operator fun invoke(
        token: String,
        body: GetFriendListRequest
    ): NetworkResult<GetFriendListResponse> {
        return repository.getFriendList(token, body)
    }
}