package com.whereareyounow.domain.usecase.feed

import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.request.feed.BookmarkFeedRequest
import kotlinx.coroutines.flow.flow

class BookmarkFeedUseCase(
    private val repository: FeedRepository
) {
    operator fun invoke(
        data: BookmarkFeedRequest
    ) = flow {
        val response = repository.bookmarkFeed(data)
        emit(response)
    }
}