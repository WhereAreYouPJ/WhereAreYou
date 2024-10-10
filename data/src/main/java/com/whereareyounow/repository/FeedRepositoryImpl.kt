package com.whereareyounow.repository

import com.google.gson.Gson
import com.whereareyounow.api.FeedApi
import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler
import com.whereareyounow.domain.entity.feed.BookmarkSeq
import com.whereareyounow.domain.entity.feed.BookmarkedFeedData
import com.whereareyounow.domain.entity.feed.FeedInfo
import com.whereareyounow.domain.entity.feed.FeedListData
import com.whereareyounow.domain.entity.feed.FeedSeq
import com.whereareyounow.domain.entity.feed.HideFeedSeq
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
import com.whereareyounow.util.UriRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class FeedRepositoryImpl(
    private val feedApi: FeedApi
) : FeedRepository, NetworkResultHandler {

    override suspend fun modifyFeed(
        data: ModifyFeedRequest
    ): NetworkResult<FeedSeq> {
        return handleResult { feedApi.modifyFeed(body = data) }
    }

    override suspend fun createFeed(
        data: CreateFeedRequest,
        images: List<Any>
    ): NetworkResult<FeedSeq> {
//        val scheduleSeq = data.scheduleSeq.toString().toRequestBody("text/plain".toMediaTypeOrNull())
//        val memberSeq = data.memberSeq.toString().toRequestBody("text/plain".toMediaTypeOrNull())
//        val title = data.title.toRequestBody("text/plain".toMediaTypeOrNull())
//        val content = data.content.toRequestBody("text/plain".toMediaTypeOrNull())
//        val feedImageOrders = data.feedImageOrders.map { it.toString().toRequestBody("text/plain".toMediaTypeOrNull()) }
//        val partMap = mutableMapOf(
//            "scheduleSeq" to scheduleSeq,
//            "memberSeq" to memberSeq,
//            "title" to title,
//            "content" to content
//        ) as HashMap<String, RequestBody>
        val reqData = Gson().toJson(data).toRequestBody("application/json".toMediaTypeOrNull())
        val multipartList = images.mapIndexed { idx, it ->
            (it as UriRequestBody).toMultipartBody("feedImageList")
        }
        return handleResult {
            feedApi.createFeed(reqData, multipartList)
        }
    }

    override suspend fun getFeedList(data: GetFeedListRequest): NetworkResult<FeedListData> {
        return handleResult {
            feedApi.getFeedList(
                memberSeq = data.memberSeq,
                page = data.page,
                size = data.size
            )
        }
    }

    override suspend fun getDetailFeed(data: GetDetailFeedRequest): NetworkResult<FeedInfo> {
        return handleResult {
            feedApi.getDetailFeed(
                memberSeq = data.memberSeq,
                scheduleSeq = data.scheduleSeq,
                feedSeq = data.feedSeq
            )
        }
    }

    override suspend fun getHidedFeed(data: GetHidedFeedRequest): NetworkResult<HidedFeedData> {
        return handleResult {
            feedApi.getHidedFeed(
                memberSeq = data.memberSeq,
                page = data.page,
                size = data.size
            )
        }
    }

    override suspend fun hideFeed(data: HideFeedRequest): NetworkResult<HideFeedSeq> {
        return handleResult {
            feedApi.hideFeed(body = data)
        }
    }

    override suspend fun restoreHidedFeed(data: RestoreHidedFeedRequest): NetworkResult<Unit> {
        return handleResult {
            feedApi.restoreHidedFeed(body = data)
        }
    }

    override suspend fun getBookmarkedFeed(data: GetBookmarkedFeedRequest): NetworkResult<BookmarkedFeedData> {
        return handleResult {
            feedApi.getBookmarkedFeed(
                memberSeq = data.memberSeq,
                page = data.page,
                size = data.size
            )
        }
    }

    override suspend fun bookmarkFeed(data: BookmarkFeedRequest): NetworkResult<BookmarkSeq> {
        return handleResult {
            feedApi.bookmarkFeed(body = data)
        }
    }

    override suspend fun deleteFeedBookmark(data: DeleteFeedBookmarkRequest): NetworkResult<String> {
        return handleResult {
            feedApi.deleteFeedBookmark(body = data)
        }
    }
}
