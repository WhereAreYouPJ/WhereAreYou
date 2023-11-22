package com.whereareyou.domain.usecase.friend

import com.whereareyou.domain.entity.apimessage.friend.GetFriendIdsListRequest
import com.whereareyou.domain.entity.apimessage.friend.GetFriendIdsListResponse
import com.whereareyou.domain.repository.FriendRepository
import com.whereareyou.domain.util.NetworkResult

class GetFriendIdsListUseCase(
    private val repository: FriendRepository
) {
    suspend operator fun invoke(
        token: String,
        body: GetFriendIdsListRequest
    ): NetworkResult<GetFriendIdsListResponse> {
        return repository.getFriendIdsList(token, body)
    }
}