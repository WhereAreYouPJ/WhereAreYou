package com.whereareyounow.domain.usecase.feed

import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.request.feed.DeleteFeedRequest
import kotlinx.coroutines.flow.flow

class DeleteFeedUseCase(
    private val repository: FeedRepository
) {
    operator fun invoke(
        data: DeleteFeedRequest
    ) = flow {
        val response = repository.deleteFeed(data)
        emit(response)
    }
}