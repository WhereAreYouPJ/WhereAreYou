package com.whereareyounow.domain.usecase.feed

import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.request.feed.GetDetailFeedRequest
import kotlinx.coroutines.flow.flow

class GetDetailFeedUseCase(
    private val repository: FeedRepository
) {
    operator fun invoke(
        data: GetDetailFeedRequest
    ) = flow {
        val response = repository.getDetailFeed(data)
        emit(response)
    }
}