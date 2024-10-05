package com.whereareyounow.domain.usecase.feed

import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.request.feed.HideFeedRequest
import kotlinx.coroutines.flow.flow

class HideFeedUseCase(
    private val repository: FeedRepository
) {
    operator fun invoke(
        data: HideFeedRequest
    ) = flow {
        val response = repository.hideFeed(data)
        emit(response)
    }
}