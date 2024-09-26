package com.onmyway.api

import com.onmyway.domain.request.friend.AcceptFriendRequestRequest
import com.onmyway.domain.request.friend.DeleteFriendRequest
import com.onmyway.domain.request.friend.SendFriendRequestRequest
import com.onmyway.domain.entity.friend.FriendInfo
import com.onmyway.domain.entity.friend.FriendRequest
import com.onmyway.domain.request.friend.RefuseFriendRequestRequest
import com.onmyway.domain.util.ResponseWrapper
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface FriendApi {

    // 친구 MemberId 목록
    @GET("friend")
    suspend fun getFriendList(
        @Query("memberSeq") memberSeq: Int
    ): Response<ResponseWrapper<List<FriendInfo>>>

    // 친구 요청 거절
    @HTTP(method = "DELETE", path = "friend", hasBody = true)
    suspend fun deleteFriend(
        @Body body: DeleteFriendRequest
    ): Response<ResponseWrapper<String>>

    // 친구 요청 리스트 조회
    @GET("friend-request")
    suspend fun getFriendRequestList(
        @Query("memberSeq") memberSeq: Int
    ): Response<ResponseWrapper<List<FriendRequest>>>

    // 친구 요청
    @POST("friend-request")
    suspend fun sendFriendRequest(
        @Body body: SendFriendRequestRequest
    ): Response<ResponseWrapper<String>>

    // 친구 요청 수락
    @POST("friend-request/accept")
    suspend fun acceptFriendRequest(
        @Body body: AcceptFriendRequestRequest
    ): Response<ResponseWrapper<Unit>>

    // 친구 삭제
    @HTTP(method = "DELETE", path = "friend-request/refuse", hasBody = true)
    suspend fun refuseFriendRequest(
        @Body body: RefuseFriendRequestRequest
    ): Response<ResponseWrapper<Unit>>
}