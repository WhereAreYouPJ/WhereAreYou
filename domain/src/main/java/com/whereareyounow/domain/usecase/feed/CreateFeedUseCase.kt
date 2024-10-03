package com.whereareyounow.domain.usecase.feed

import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.request.feed.CreateFeedRequest
import kotlinx.coroutines.flow.flow

class CreateFeedUseCase(
    private val repository: FeedRepository
) {
    operator fun invoke(
        data: CreateFeedRequest
    ) = flow {
        val response = repository.createFeed(data)
        emit(response)
    }
}