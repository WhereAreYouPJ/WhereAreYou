package com.whereareyounow.domain.repository

import com.whereareyounow.domain.entity.friend.FriendInfo
import com.whereareyounow.domain.entity.friend.FriendRequest
import com.whereareyounow.domain.request.friend.AcceptFriendRequestRequest
import com.whereareyounow.domain.request.friend.DeleteFriendRequest
import com.whereareyounow.domain.request.friend.GetFriendListRequest
import com.whereareyounow.domain.request.friend.GetFriendRequestListRequest
import com.whereareyounow.domain.request.friend.RefuseFriendRequestRequest
import com.whereareyounow.domain.request.friend.SendFriendRequestRequest
import com.whereareyounow.domain.util.NetworkResult

interface FriendRepository {

    // 친구 MemberId 목록
    suspend fun getFriendList(
        data: GetFriendListRequest
    ): NetworkResult<List<FriendInfo>>

    // 친구 요청 거절
    suspend fun deleteFriend(
        data: DeleteFriendRequest
    ): NetworkResult<String>

    // 친구 요청 리스트 조회
    suspend fun getFriendRequestList(
        data: GetFriendRequestListRequest
    ): NetworkResult<List<FriendRequest>>

    // 친구 요청
    suspend fun sendFriendRequest(
        data: SendFriendRequestRequest
    ): NetworkResult<String>

    // 친구 요청 수락
    suspend fun acceptFriendRequest(
        data: AcceptFriendRequestRequest
    ): NetworkResult<Unit>

    // 친구 삭제
    suspend fun refuseFriendRequest(
        data: RefuseFriendRequestRequest
    ): NetworkResult<Unit>
}