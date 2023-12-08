package com.whereareyounow.repository

import com.whereareyounow.datasource.RemoteDataSource
import com.whereareyounow.domain.entity.apimessage.friend.AcceptFriendRequestRequest
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendIdsListRequest
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendIdsListResponse
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendListRequest
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendListResponse
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendRequestListResponse
import com.whereareyounow.domain.entity.apimessage.friend.RefuseFriendRequestRequest
import com.whereareyounow.domain.entity.apimessage.friend.SendFriendRequestRequest
import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler

class FriendRepositoryImpl(
    private val dataSource: RemoteDataSource
) : FriendRepository, NetworkResultHandler {

    /**
     * 친구 MemberId 목록
     * [FriendRepository.getFriendIdsList]
     */
    override suspend fun getFriendIdsList(
        token: String,
        body: GetFriendIdsListRequest
    ): NetworkResult<GetFriendIdsListResponse> {
        return handleResult { dataSource.getFriendIdsList(token, body) }
    }

    /**
     * 친구 목록
     * [FriendRepository.getFriendList]
     */
    override suspend fun getFriendList(
        token: String,
        body: GetFriendListRequest
    ): NetworkResult<GetFriendListResponse> {
        return handleResult { dataSource.getFriendList(token, body) }
    }

    /**
     * 친구 요청 조회(사이드바)
     * [FriendRepository.getFriendRequestList]
     */
    override suspend fun getFriendRequestList(
        token: String,
        memberId: String
    ): NetworkResult<GetFriendRequestListResponse> {
        return handleResult { dataSource.getFriendRequestList(token, memberId) }
    }

    /**
     * 친구 신청
     * [FriendRepository.sendFriendRequest]
     */
    override suspend fun sendFriendRequest(
        token: String,
        body: SendFriendRequestRequest
    ): NetworkResult<Unit> {
        return handleResult { dataSource.sendFriendRequest(token, body) }
    }

    /**
     * 친구 수락
     * [FriendRepository.acceptFriendRequest]
     */
    override suspend fun acceptFriendRequest(
        token: String,
        body: AcceptFriendRequestRequest
    ): NetworkResult<Unit> {
        return handleResult { dataSource.acceptFriendRequest(token, body) }
    }

    /**
     * 친구 거절
     * [FriendRepository.refuseFriendRequest]
     */
    override suspend fun refuseFriendRequest(
        token: String,
        body: RefuseFriendRequestRequest
    ): NetworkResult<Unit> {
        return handleResult { dataSource.refuseFriendRequest(token, body) }
    }
}