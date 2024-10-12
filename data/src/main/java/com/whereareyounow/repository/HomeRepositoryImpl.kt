package com.whereareyounow.repository

import com.whereareyounow.api.HomeApi
import com.whereareyounow.domain.repository.HomeRepository
import com.whereareyounow.domain.request.home.GetHomeImageListRequest
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler

class HomeRepositoryImpl(
    private val homeApi: HomeApi
) : HomeRepository, NetworkResultHandler {

    override suspend fun getHomeImageList(data: GetHomeImageListRequest): NetworkResult<List<String>> {
        return handleResult { homeApi.getHomeImageList(memberSeq = data.memberSeq) }
    }
}