package com.whereareyounow.repository

import com.whereareyounow.api.FriendApi
import com.whereareyounow.domain.entity.friend.FriendInfo
import com.whereareyounow.domain.entity.friend.FriendRequest
import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.request.friend.AcceptFriendRequestRequest
import com.whereareyounow.domain.request.friend.DeleteFriendRequest
import com.whereareyounow.domain.request.friend.GetFriendListRequest
import com.whereareyounow.domain.request.friend.GetFriendRequestListRequest
import com.whereareyounow.domain.request.friend.RefuseFriendRequestRequest
import com.whereareyounow.domain.request.friend.SendFriendRequestRequest
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler

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