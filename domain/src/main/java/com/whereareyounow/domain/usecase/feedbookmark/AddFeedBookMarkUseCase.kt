package com.whereareyounow.domain.usecase.feedbookmark

import com.whereareyounow.domain.repository.BookMarkFeedRepository
import com.whereareyounow.domain.request.feedbookmark.AddFeedBookMarkRequest
import kotlinx.coroutines.flow.flow

class AddFeedBookMarkUseCase(
    private val repository : BookMarkFeedRepository
) {
    operator fun invoke(
        data: AddFeedBookMarkRequest
    ) = flow {
        val response = repository.addFeedBookMark(data)
        emit(response)
    }
}