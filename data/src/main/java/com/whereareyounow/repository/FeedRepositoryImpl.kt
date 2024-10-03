package com.whereareyounow.repository

import com.whereareyounow.api.FeedApi
import com.whereareyounow.domain.entity.feed.FeedBookMarkResponse
import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.request.feed.GetFeedBookMarkRequest
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler

class FeedRepositoryImpl(
    private val feedApi: FeedApi
) : FeedRepository , NetworkResultHandler{
    override suspend fun getFeedBookMark(
        getFeedBookMarkRequest: GetFeedBookMarkRequest
    ): NetworkResult<FeedBookMarkResponse> {

        return handleResult { feedApi.getFeedList(
            memberSeq = getFeedBookMarkRequest.memberSeq,
            page = getFeedBookMarkRequest.page,
            size = getFeedBookMarkRequest.size
        ) }

    }
}