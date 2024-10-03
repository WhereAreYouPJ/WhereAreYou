package com.whereareyounow.domain.repository

import com.whereareyounow.domain.entity.feedbookmark.AddFeedBookMark
import com.whereareyounow.domain.entity.feedbookmark.GetFeedBookMarkResponse
import com.whereareyounow.domain.request.feedbookmark.AddFeedBookMarkRequest
import com.whereareyounow.domain.request.feedbookmark.GetFeedBookMarkRequest
import com.whereareyounow.domain.request.feedbookmark.RestoreFeedBookMarkRequest
import com.whereareyounow.domain.util.NetworkResult

interface BookMarkFeedRepository {

    suspend fun getFeedBookMark(
        getFeedBookMarkRequest : GetFeedBookMarkRequest
    ) : NetworkResult<GetFeedBookMarkResponse>

    suspend fun addFeedBookMark(
        addFeedBookMarkRequest: AddFeedBookMarkRequest
    ) : NetworkResult<AddFeedBookMark>

    suspend fun restoreFeedBookMark(
        restoreFeedBookMarkRequest: RestoreFeedBookMarkRequest
    ) : NetworkResult<String>

}