package com.whereareyounow.repository

import com.whereareyounow.api.FeedBookMarkApi
import com.whereareyounow.domain.entity.feedbookmark.AddFeedBookMark
import com.whereareyounow.domain.entity.feedbookmark.GetFeedBookMarkResponse
import com.whereareyounow.domain.repository.BookMarkFeedRepository
import com.whereareyounow.domain.request.feedbookmark.AddFeedBookMarkRequest
import com.whereareyounow.domain.request.feedbookmark.GetFeedBookMarkRequest
import com.whereareyounow.domain.request.feedbookmark.RestoreFeedBookMarkRequest
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler

class BookMarkFeedRepositoryImpl (
    private val feedBookMarkApi: FeedBookMarkApi
) : BookMarkFeedRepository, NetworkResultHandler{

    override suspend fun getFeedBookMark(
        getFeedBookMarkRequest: GetFeedBookMarkRequest
    ): NetworkResult<GetFeedBookMarkResponse> {

        return handleResult { feedBookMarkApi.getBookMarkFeedList(
            memberSeq = getFeedBookMarkRequest.memberSeq,
            page = getFeedBookMarkRequest.page,
            size = getFeedBookMarkRequest.size
        ) }

    }

    override suspend fun addFeedBookMark(
        addFeedBookMarkRequest: AddFeedBookMarkRequest
    ): NetworkResult<AddFeedBookMark> {
        return handleResult { feedBookMarkApi.addBookMarkFeed(
            body = addFeedBookMarkRequest
        ) }
    }

    override suspend fun restoreFeedBookMark(
        restoreFeedBookMarkRequest: RestoreFeedBookMarkRequest
    ): NetworkResult<String> {
        return handleResult { feedBookMarkApi.restoreBookMarkFeed(
            body = restoreFeedBookMarkRequest
        ) }
    }


}