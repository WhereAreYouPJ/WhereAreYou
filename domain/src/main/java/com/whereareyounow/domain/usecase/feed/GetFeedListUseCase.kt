package com.whereareyounow.domain.usecase.feed

import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.request.feed.GetFeedListRequest
import kotlinx.coroutines.flow.flow

class GetFeedListUseCase(
    private val repository: FeedRepository
) {
    operator fun invoke(
        data: GetFeedListRequest
    ) = flow {
        val response = repository.getFeedList(data)
        emit(response)
    }
}