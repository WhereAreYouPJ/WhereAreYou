package com.whereareyounow.domain.usecase.feed

import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.request.feed.GetBookmarkedFeedRequest
import kotlinx.coroutines.flow.flow

class GetBookmarkedFeedUseCase(
    private val repository: FeedRepository
) {
    operator fun invoke(
        data: GetBookmarkedFeedRequest
    ) = flow {
        val response = repository.getBookmarkedFeed(data)
        emit(response)
    }
}