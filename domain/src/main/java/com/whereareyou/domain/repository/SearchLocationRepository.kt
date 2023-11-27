package com.whereareyou.domain.repository

import com.whereareyou.domain.entity.apimessage.schedule.GetLocationAddressResponse
import com.whereareyou.domain.util.NetworkResult

interface SearchLocationRepository {

    suspend fun getLocationAddress(
        query: String
    ): NetworkResult<GetLocationAddressResponse>
}