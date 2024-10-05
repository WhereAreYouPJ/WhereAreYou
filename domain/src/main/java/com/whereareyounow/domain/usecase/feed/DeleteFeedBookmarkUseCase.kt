package com.whereareyounow.domain.usecase.feed

import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.request.feed.DeleteFeedBookmarkRequest
import kotlinx.coroutines.flow.flow

class DeleteFeedBookmarkUseCase(
    private val repository: FeedRepository
) {
    operator fun invoke(
        data: DeleteFeedBookmarkRequest
    ) = flow {
        val response = repository.deleteFeedBookmark(data)
        emit(response)
    }
}