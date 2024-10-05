package com.whereareyounow.domain.usecase.feed

import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.request.feed.RestoreHidedFeedRequest
import kotlinx.coroutines.flow.flow

class RestoreHidedFeedUseCase(
    private val repository: FeedRepository
) {
    operator fun invoke(
        data: RestoreHidedFeedRequest
    ) = flow {
        val response = repository.restoreHidedFeed(data)
        emit(response)
    }
}