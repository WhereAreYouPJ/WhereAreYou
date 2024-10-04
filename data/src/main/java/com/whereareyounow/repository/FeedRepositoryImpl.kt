package com.whereareyounow.repository

import com.whereareyounow.api.FeedApi
import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.whereareyounow.domain.entity.feed.FeedInfo
import com.whereareyounow.domain.entity.feed.FeedListData
import com.whereareyounow.domain.entity.feed.FeedSeq
import com.whereareyounow.domain.request.feed.CreateFeedRequest
import com.whereareyounow.domain.request.feed.GetDetailFeedRequest
import com.whereareyounow.domain.request.feed.GetFeedListRequest
import com.whereareyounow.domain.request.feed.ModifyFeedRequest
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class FeedRepositoryImpl(
    private val feedApi: FeedApi
) : FeedRepository, NetworkResultHandler {
//    override suspend fun getFeedBookMark(
//        getFeedBookMarkRequest: GetFeedBookMarkRequest
//    ): NetworkResult<FeedBookMarkResponse> {
//
//        return handleResult { feedApi.getFeedList(
//            memberSeq = getFeedBookMarkRequest.memberSeq,
//            page = getFeedBookMarkRequest.page,
//            size = getFeedBookMarkRequest.size
//        ) }
//    }

    override suspend fun modifyFeed(
        data: ModifyFeedRequest
    ): NetworkResult<FeedSeq> {
        return handleResult { feedApi.modifyFeed(body = data) }
    }

    override suspend fun createFeed(
        data: CreateFeedRequest
    ): NetworkResult<FeedSeq> {
        val scheduleSeq =
            data.scheduleSeq.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val memberSeq = data.memberSeq.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val title = data.title.toRequestBody("text/plain".toMediaTypeOrNull())
        val content = data.content.toRequestBody("text/plain".toMediaTypeOrNull())
        val partMap = mutableMapOf(
            "scheduleSeq" to scheduleSeq,
            "memberSeq" to memberSeq,
            "title" to title,
            "content" to content
        ) as HashMap<String, RequestBody>
        val feedImageInfos = data.feedImageInfos.map { imageInfo ->
            val multipartImage: MultipartBody.Part = imageInfo.image.let { image ->
                val imageBody = image.asRequestBody("image/png".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("multipartFile", imageBody.toString(), imageBody)
            }
            ImageMultipart(
                multipartImage,
                imageInfo.feedImageOrder
            )
        }
        val images = feedImageInfos.map {
            val body = Gson().toJson(it).toRequestBody("multipart/formed-data".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("feedImageInfos", "feedImageInfos", body)
        }
        return handleResult {
            feedApi.createFeed(partMap, images)
        }
    }

//    override suspend fun getFeedList(
//        data: GetFeedListRequest
//    ): NetworkResult<FeedListData> {
//        return handleResult {  }
//    }
//
//    override suspend fun getDetailFeed(
//        data: GetDetailFeedRequest
//    ): NetworkResult<FeedInfo> {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun getFeedBookMark(
//        getFeedBookMarkRequest: GetFeedBookMarkRequest
//    ): NetworkResult<List<FeedBookMarkResponse>> {
//        TODO("Not yet implemented")
//    }
}

data class ImageMultipart(
    @SerializedName("images")
    val images: MultipartBody.Part,
    @SerializedName("feedImageOrder")
    val feedImageOrder: Int,
)
) : FeedRepository , NetworkResultHandler {

}
