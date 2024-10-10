package com.whereareyounow.domain.repository

import com.whereareyounow.domain.entity.feed.BookmarkSeq
import com.whereareyounow.domain.entity.feed.BookmarkedFeedData
import com.whereareyounow.domain.entity.feed.FeedInfo
import com.whereareyounow.domain.entity.feed.FeedListData
import com.whereareyounow.domain.entity.feed.FeedSeq
import com.whereareyounow.domain.entity.feed.HideFeedSeq
import com.whereareyounow.domain.entity.feed.HidedFeed
import com.whereareyounow.domain.entity.feed.HidedFeedData
import com.whereareyounow.domain.request.feed.BookmarkFeedRequest
import com.whereareyounow.domain.request.feed.CreateFeedRequest
import com.whereareyounow.domain.request.feed.DeleteFeedBookmarkRequest
import com.whereareyounow.domain.request.feed.GetBookmarkedFeedRequest
import com.whereareyounow.domain.request.feed.GetDetailFeedRequest
import com.whereareyounow.domain.request.feed.GetFeedListRequest
import com.whereareyounow.domain.request.feed.GetHidedFeedRequest
import com.whereareyounow.domain.request.feed.HideFeedRequest
import com.whereareyounow.domain.request.feed.ModifyFeedRequest
import com.whereareyounow.domain.request.feed.RestoreHidedFeedRequest
import com.whereareyounow.domain.request.feedbookmark.GetFeedBookMarkRequest
import com.whereareyounow.domain.util.NetworkResult
import java.io.File

interface FeedRepository {

    suspend fun modifyFeed(
        data: ModifyFeedRequest
    ): NetworkResult<FeedSeq>

    suspend fun createFeed(
        data: CreateFeedRequest,
        images: List<Any>
    ): NetworkResult<FeedSeq>

    suspend fun getFeedList(
        data: GetFeedListRequest
    ): NetworkResult<FeedListData>

    suspend fun getDetailFeed(
        data: GetDetailFeedRequest
    ): NetworkResult<FeedInfo>

    suspend fun getHidedFeed(
        data: GetHidedFeedRequest
    ): NetworkResult<HidedFeedData>

    suspend fun hideFeed(
        data: HideFeedRequest
    ): NetworkResult<HideFeedSeq>

    suspend fun restoreHidedFeed(
        data: RestoreHidedFeedRequest
    ): NetworkResult<Unit>

    suspend fun getBookmarkedFeed(
        data: GetBookmarkedFeedRequest
    ): NetworkResult<BookmarkedFeedData>

    suspend fun bookmarkFeed(
        data: BookmarkFeedRequest
    ): NetworkResult<BookmarkSeq>

    suspend fun deleteFeedBookmark(
        data: DeleteFeedBookmarkRequest
    ): NetworkResult<String>
}