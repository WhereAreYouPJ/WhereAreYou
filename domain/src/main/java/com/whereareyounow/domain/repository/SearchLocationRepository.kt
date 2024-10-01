package com.whereareyounow.domain.repository

import com.whereareyounow.domain.entity.location.SearchLocationResult
import com.whereareyounow.domain.request.location.SearchLocationAddressRequest
import com.whereareyounow.domain.util.NetworkResult

interface SearchLocationRepository {
    suspend fun searchLocationAddress(
        data: SearchLocationAddressRequest
    ): NetworkResult<SearchLocationResult>
}