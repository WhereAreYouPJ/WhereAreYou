package com.onmyway.repository

import com.onmyway.api.FriendApi
import com.onmyway.domain.entity.friend.FriendInfo
import com.onmyway.domain.entity.friend.FriendRequest
import com.onmyway.domain.repository.FriendRepository
import com.onmyway.domain.request.friend.AcceptFriendRequestRequest
import com.onmyway.domain.request.friend.DeleteFriendRequest
import com.onmyway.domain.request.friend.GetFriendListRequest
import com.onmyway.domain.request.friend.GetFriendRequestListRequest
import com.onmyway.domain.request.friend.RefuseFriendRequestRequest
import com.onmyway.domain.request.friend.SendFriendRequestRequest
import com.onmyway.domain.util.NetworkResult
import com.onmyway.util.NetworkResultHandler

class FriendRepositoryImpl(
    private val friendApi: FriendApi
) : FriendRepository, NetworkResultHandler {

    override suspend fun getFriendList(
        data: GetFriendListRequest
    ): NetworkResult<List<FriendInfo>> {
        return handleResult { friendApi.getFriendList(
            memberSeq = data.memberSeq
        ) }
    }

    override suspend fun deleteFriend(
        data: DeleteFriendRequest
    ): NetworkResult<String> {
        return handleResult { friendApi.deleteFriend(body = data) }
    }

    override suspend fun getFriendRequestList(
        data: GetFriendRequestListRequest
    ): NetworkResult<List<FriendRequest>> {
        return handleResult { friendApi.getFriendRequestList(
            memberSeq = data.memberSeq
        ) }
    }

    override suspend fun sendFriendRequest(
        data: SendFriendRequestRequest
    ): NetworkResult<String> {
        return handleResult { friendApi.sendFriendRequest(body = data) }
    }

    override suspend fun acceptFriendRequest(
        data: AcceptFriendRequestRequest
    ): NetworkResult<Unit> {
        return handleResult { friendApi.acceptFriendRequest(body = data) }
    }

    override suspend fun refuseFriendRequest(
        data: RefuseFriendRequestRequest
    ): NetworkResult<Unit> {
        return handleResult { friendApi.refuseFriendRequest(body = data) }
    }
}