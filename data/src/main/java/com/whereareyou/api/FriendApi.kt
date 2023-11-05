package com.whereareyou.api

import com.whereareyou.domain.entity.apimessage.friend.AcceptFriendRequestRequest
import com.whereareyou.domain.entity.apimessage.friend.GetFriendIdsListRequest
import com.whereareyou.domain.entity.apimessage.friend.GetFriendIdsListResponse
import com.whereareyou.domain.entity.apimessage.friend.GetFriendListRequest
import com.whereareyou.domain.entity.apimessage.friend.GetFriendListResponse
import com.whereareyou.domain.entity.apimessage.friend.GetFriendRequestListResponse
import com.whereareyou.domain.entity.apimessage.friend.RefuseFriendRequestRequest
import com.whereareyou.domain.entity.apimessage.friend.SendFriendRequestRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface FriendApi {

    // 친구 MemberId 목록
    @POST("friend/friendIds")
    suspend fun getFriendIdsList(
        @Header("Authorization") token: String,
        @Body body: GetFriendIdsListRequest
    ): Response<GetFriendIdsListResponse>

    // 친구 목록
    @POST("friend/friendList")
    suspend fun getFriendList(
        @Header("Authorization") token: String,
        @Body body: GetFriendListRequest
    ): Response<GetFriendListResponse>

    // 친구 요청 조회(사이드바)
    @GET("friend/requestList")
    suspend fun getFriendRequestList(
        @Header("Authorization") token: String,
        @Query("memberId") memberId: String
    ): Response<GetFriendRequestListResponse>

    // 친구 신청
    @POST("friend/request")
    suspend fun sendFriendRequest(
        @Header("Authorization") token: String,
        @Body body: SendFriendRequestRequest
    ): Response<Nothing>

    // 친구 수락
    @POST("friend/accept")
    suspend fun acceptFriendRequest(
        @Header("Authorization") token: String,
        @Body body: AcceptFriendRequestRequest
    ): Response<Nothing>

    // 친구 거절
    @POST("friend/refuse")
    suspend fun refuseFriendRequest(
        @Header("Authorization") token: String,
        @Body body: RefuseFriendRequestRequest
    ): Response<Nothing>

    // 친구 삭제

}