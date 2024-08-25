package com.whereareyounow.api

import com.whereareyounow.domain.entity.feed.FeedListData
import com.whereareyounow.domain.util.ResponseWrapper
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedApi {

    // 피드 리스트 조회
    @GET("feed")
    suspend fun getFeedList(
        @Query("memberSeq") memberSeq: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ResponseWrapper<FeedListData>>
}