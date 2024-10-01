package com.whereareyounow.domain.repository

import com.whereareyounow.domain.entity.feed.FeedBookMarkResponse
import com.whereareyounow.domain.entity.location.LocationFaboriteInfo
import com.whereareyounow.domain.request.feed.GetFeedBookMarkRequest
import com.whereareyounow.domain.util.NetworkResult

interface FeedRepository {
    suspend fun getFeedBookMark(
        getFeedBookMarkRequest : GetFeedBookMarkRequest
    ) : NetworkResult<List<FeedBookMarkResponse>>
}