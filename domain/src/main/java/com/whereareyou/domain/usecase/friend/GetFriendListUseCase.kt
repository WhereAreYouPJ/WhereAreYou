package com.whereareyou.domain.usecase.friend

import com.whereareyou.domain.entity.apimessage.friend.GetFriendListRequest
import com.whereareyou.domain.entity.apimessage.friend.GetFriendListResponse
import com.whereareyou.domain.repository.FriendRepository
import com.whereareyou.domain.util.NetworkResult

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