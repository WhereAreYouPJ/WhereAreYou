package com.whereareyounow.api

import com.whereareyounow.domain.entity.feedbookmark.AddFeedBookMark
import com.whereareyounow.domain.entity.feedbookmark.GetFeedBookMarkResponse
import com.whereareyounow.domain.request.feedbookmark.AddFeedBookMarkRequest
import com.whereareyounow.domain.request.feedbookmark.RestoreFeedBookMarkRequest
import com.whereareyounow.domain.request.friend.RefuseFriendRequestRequest
import com.whereareyounow.domain.util.ResponseWrapper
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface FeedBookMarkApi {

    // 북마크 피드 리스트 조회
    @GET("book-mark")
    suspend fun getBookMarkFeedList(
        @Query("memberSeq") memberSeq: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ResponseWrapper<GetFeedBookMarkResponse>>

    // 북마크 피드 추가
    @POST("book-mark")
    suspend fun addBookMarkFeed(
        @Body body: AddFeedBookMarkRequest
    ) : Response<ResponseWrapper<AddFeedBookMark>>

    @HTTP(method = "DELETE", path = "book-mark", hasBody = true)
    suspend fun restoreBookMarkFeed(
        @Body body: RestoreFeedBookMarkRequest
    ): Response<ResponseWrapper<String>>
}