package com.whereareyounow.api

import com.whereareyounow.domain.entity.feed.BookmarkSeq
import com.whereareyounow.domain.entity.feed.BookmarkedFeedData
import com.whereareyounow.domain.entity.feed.FeedInfo
import com.whereareyounow.domain.entity.feed.FeedListData
import com.whereareyounow.domain.entity.feed.FeedSeq
import com.whereareyounow.domain.entity.feed.HideFeedSeq
import com.whereareyounow.domain.entity.feed.HidedFeedData
import com.whereareyounow.domain.request.feed.BookmarkFeedRequest
import com.whereareyounow.domain.request.feed.DeleteFeedBookmarkRequest
import com.whereareyounow.domain.request.feed.DeleteFeedRequest
import com.whereareyounow.domain.request.feed.HideFeedRequest
import com.whereareyounow.domain.request.feed.ModifyFeedRequest
import com.whereareyounow.domain.request.feed.RestoreHidedFeedRequest
import com.whereareyounow.domain.util.ResponseWrapper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Query

interface FeedApi {

    // 피드 수정
    @PUT("feed")
    suspend fun modifyFeed(
        @Body body: ModifyFeedRequest
    ): Response<ResponseWrapper<FeedSeq>>

    // 피드 생성
    @Multipart
    @POST("feed")
    suspend fun createFeed(
        @Part("request") reqData: RequestBody,
        @Part images: List<MultipartBody.Part>?
    ): Response<ResponseWrapper<FeedSeq>>

    // 피드 삭제
    @HTTP(method = "DELETE", path = "feed", hasBody = true)
    suspend fun deleteFeed(
        @Body body: DeleteFeedRequest
    ): Response<ResponseWrapper<String>>

    // 피드 리스트 조회
    @GET("feed/list")
    suspend fun getFeedList(
        @Query("memberSeq") memberSeq: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ResponseWrapper<FeedListData>>

    // 피드 상세 조회
    @GET("feed/details")
    suspend fun getDetailFeed(
        @Query("memberSeq") memberSeq: Int,
        @Query("scheduleSeq") scheduleSeq: Int,
        @Query("feedSeq") feedSeq: Int
    ): Response<ResponseWrapper<FeedInfo>>

    // 숨긴 피드 조회
    @GET("hide-feed")
    suspend fun getHidedFeed(
        @Query("memberSeq") memberSeq: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ResponseWrapper<HidedFeedData>>

    // 피드 숨김
    @POST("hide-feed")
    suspend fun hideFeed(
        @Body body: HideFeedRequest
    ): Response<ResponseWrapper<String>>

    // 숨긴 피드 복원
    @HTTP(method = "DELETE", path = "hide-feed", hasBody = true)
    suspend fun restoreHidedFeed(
        @Body body: RestoreHidedFeedRequest
    ): Response<ResponseWrapper<String>>

    // 피드 책갈피 조회
    @GET("book-mark")
    suspend fun getBookmarkedFeed(
        @Query("memberSeq") memberSeq: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ResponseWrapper<BookmarkedFeedData>>

    // 책갈피 추가
    @POST("book-mark")
    suspend fun bookmarkFeed(
        @Body body: BookmarkFeedRequest
    ): Response<ResponseWrapper<String>>

    // 피드 책갈피 복원(삭제)
    @HTTP(method = "DELETE", path = "book-mark", hasBody = true)
    suspend fun deleteFeedBookmark(
        @Body body: DeleteFeedBookmarkRequest
    ): Response<ResponseWrapper<String>>
}