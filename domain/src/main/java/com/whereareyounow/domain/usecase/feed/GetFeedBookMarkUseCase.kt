package com.whereareyounow.domain.usecase.feed

import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.request.feed.GetFeedBookMarkRequest
import kotlinx.coroutines.flow.flow

//class GetFeedBookMarkUseCase (
//    private val repository: FeedRepository
//) {
//    operator fun invoke(
//        data : GetFeedBookMarkRequest
//    ) = flow {
//        val response = repository.getFeedBookMark(data)
//        emit(response)
//    }
//}