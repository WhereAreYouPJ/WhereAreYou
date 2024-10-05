package com.whereareyounow.domain.usecase.feed

import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.request.feed.GetHidedFeedRequest
import kotlinx.coroutines.flow.flow

class GetHidedFeedUseCase(
    private val repository: FeedRepository
) {
    operator fun invoke(
        data: GetHidedFeedRequest
    ) = flow {
        val response = repository.getHidedFeed(data)
        emit(response)
    }
}