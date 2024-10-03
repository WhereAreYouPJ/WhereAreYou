package com.whereareyounow.domain.usecase.feedbookmark

import com.whereareyounow.domain.repository.BookMarkFeedRepository
import com.whereareyounow.domain.request.feedbookmark.GetFeedBookMarkRequest
import kotlinx.coroutines.flow.flow

class GetFeedBookMarkUseCase (
    private val repository: BookMarkFeedRepository
) {
    operator fun invoke(
        data : GetFeedBookMarkRequest
    ) = flow {
        val response = repository.getFeedBookMark(data)
        emit(response)
    }
}