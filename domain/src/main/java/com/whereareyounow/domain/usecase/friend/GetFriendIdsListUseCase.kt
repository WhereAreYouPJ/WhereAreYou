package com.whereareyounow.domain.usecase.friend

import com.whereareyounow.domain.entity.apimessage.friend.GetFriendIdsListRequest
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendIdsListResponse
import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.util.NetworkResult

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