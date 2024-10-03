package com.whereareyounow.api

import com.whereareyounow.domain.entity.feed.FeedBookMarkResponse
import com.whereareyounow.domain.entity.feed.FeedInfo
import com.whereareyounow.domain.entity.feed.FeedListData
import com.whereareyounow.domain.entity.feed.FeedSeq
import com.whereareyounow.domain.request.feed.ModifyFeedRequest
import com.whereareyounow.domain.util.ResponseWrapper
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
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
        @PartMap partMap: HashMap<String, RequestBody>,
        @Part images: List<MultipartBody.Part>?
    ): Response<ResponseWrapper<FeedSeq>>

    // 피드 리스트 조회
    @GET("book-mark")
    suspend fun getFeedList(
        @Query("memberSeq") memberSeq: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ResponseWrapper<FeedListData>>

    // 피드 상세 조회
    suspend fun getDetailFeed(
        @Query("memberSeq") memberSeq: Int,
        @Query("feedSeq") feedSeq: Int
    ): Response<ResponseWrapper<FeedInfo>>
}