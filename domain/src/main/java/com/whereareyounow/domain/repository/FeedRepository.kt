package com.whereareyounow.domain.repository

import android.net.Network
import com.whereareyounow.domain.entity.feed.FeedBookMarkResponse
import com.whereareyounow.domain.entity.feed.FeedInfo
import com.whereareyounow.domain.entity.feed.FeedListData
import com.whereareyounow.domain.entity.feed.FeedSeq
import com.whereareyounow.domain.request.feed.CreateFeedRequest
import com.whereareyounow.domain.request.feed.GetDetailFeedRequest
import com.whereareyounow.domain.request.feed.GetFeedBookMarkRequest
import com.whereareyounow.domain.request.feed.GetFeedListRequest
import com.whereareyounow.domain.request.feed.ModifyFeedRequest
import com.whereareyounow.domain.util.NetworkResult

interface FeedRepository {
//    suspend fun getFeedBookMark(
//        getFeedBookMarkRequest : GetFeedBookMarkRequest
//    ) : NetworkResult<FeedBookMarkResponse>

    suspend fun modifyFeed(
        data: ModifyFeedRequest
    ): NetworkResult<FeedSeq>

    suspend fun createFeed(
        data: CreateFeedRequest
    ): NetworkResult<FeedSeq>

//    suspend fun getFeedList(
//        data: GetFeedListRequest
//    ): NetworkResult<FeedListData>
//
//    suspend fun getDetailFeed(
//        data: GetDetailFeedRequest
//    ): NetworkResult<FeedInfo>
//
//    suspend fun getFeedBookMark(
//        getFeedBookMarkRequest : GetFeedBookMarkRequest
//    ) : NetworkResult<List<FeedBookMarkResponse>>
}