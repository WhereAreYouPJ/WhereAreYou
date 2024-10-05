package com.whereareyounow.domain.usecase.feed

import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.request.feed.ModifyFeedRequest
import kotlinx.coroutines.flow.flow

class ModifyFeedUseCase(
    private val repository: FeedRepository
) {
    operator fun invoke(
        data: ModifyFeedRequest
    ) = flow {
        val response = repository.modifyFeed(data)
        emit(response)
    }
}