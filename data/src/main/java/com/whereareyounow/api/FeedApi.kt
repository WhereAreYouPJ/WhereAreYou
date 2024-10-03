package com.whereareyounow.api

import com.whereareyounow.domain.entity.feed.FeedSeq
import com.whereareyounow.domain.request.feed.ModifyFeedRequest
import com.whereareyounow.domain.util.ResponseWrapper
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PUT

interface FeedApi {

    // 피드 수정
    @PUT("feed")
    suspend fun modifyFeed(
        @Body body: ModifyFeedRequest
    ): Response<ResponseWrapper<FeedSeq>>

}