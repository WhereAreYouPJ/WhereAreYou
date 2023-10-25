package com.whereareyou.domain.repository

import com.whereareyou.domain.entity.schedule.LocationInformation
import com.whereareyou.domain.util.NetworkResult

interface SearchLocationRepository {

    suspend fun getLocationAddress(
        query: String
    ): NetworkResult<List<LocationInformation>>
}