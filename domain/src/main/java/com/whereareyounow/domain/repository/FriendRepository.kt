package com.whereareyounow.domain.repository

import com.whereareyounow.domain.entity.apimessage.friend.AcceptFriendRequestRequest
import com.whereareyounow.domain.entity.apimessage.friend.DeleteFriendRequest
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendIdsListRequest
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendIdsListResponse
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendListRequest
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendListResponse
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendRequestListResponse
import com.whereareyounow.domain.entity.apimessage.friend.RefuseFriendRequestRequest
import com.whereareyounow.domain.entity.apimessage.friend.SendFriendRequestRequest
import com.whereareyounow.domain.util.NetworkResult

interface FriendRepository {

    // 친구 MemberId 목록
    suspend fun getFriendIdsList(
        token: String,
        body: GetFriendIdsListRequest
    ): NetworkResult<GetFriendIdsListResponse>

    // 친구 목록
    suspend fun getFriendList(
        token: String,
        body: GetFriendListRequest
    ): NetworkResult<GetFriendListResponse>

    // 친구 요청 조회(사이드바)
    suspend fun getFriendRequestList(
        token: String,
        memberId: String
    ): NetworkResult<GetFriendRequestListResponse>

    // 친구 신청
    suspend fun sendFriendRequest(
        token: String,
        body: SendFriendRequestRequest
    ): NetworkResult<Unit>

    // 친구 수락
    suspend fun acceptFriendRequest(
        token: String,
        body: AcceptFriendRequestRequest
    ): NetworkResult<Unit>

    // 친구 거절
    suspend fun refuseFriendRequest(
        token: String,
        body: RefuseFriendRequestRequest
    ): NetworkResult<Unit>

    // 친구 삭제
    suspend fun deleteFriend(
        token: String,
        body: DeleteFriendRequest
    ): NetworkResult<Unit>
}