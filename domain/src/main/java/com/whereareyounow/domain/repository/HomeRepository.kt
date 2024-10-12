package com.whereareyounow.domain.repository

import com.whereareyounow.domain.entity.home.ImageUrl
import com.whereareyounow.domain.request.home.GetHomeImageListRequest
import com.whereareyounow.domain.util.NetworkResult

interface HomeRepository {

    suspend fun getHomeImageList(
        data: GetHomeImageListRequest
    ): NetworkResult<List<ImageUrl>>
}