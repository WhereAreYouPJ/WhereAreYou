package com.whereareyounow.repository

import com.whereareyounow.api.FeedApi
import com.whereareyounow.domain.repository.FeedRepository
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler

class FeedRepositoryImpl(
    private val feedApi: FeedApi
) : FeedRepository , NetworkResultHandler {

}