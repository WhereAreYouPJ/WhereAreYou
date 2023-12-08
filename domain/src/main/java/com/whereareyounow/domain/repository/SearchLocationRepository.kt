package com.whereareyounow.domain.repository

import com.whereareyounow.domain.entity.apimessage.schedule.GetLocationAddressResponse
import com.whereareyounow.domain.util.NetworkResult

interface SearchLocationRepository {

    suspend fun getLocationAddress(
        query: String
    ): NetworkResult<GetLocationAddressResponse>
}