package com.whereareyounow.repository

import com.whereareyounow.api.SearchLocationApi
import com.whereareyounow.domain.entity.location.SearchLocationResult
import com.whereareyounow.domain.entity.schedule.LocationInformation
import com.whereareyounow.domain.repository.SearchLocationRepository
import com.whereareyounow.domain.request.location.SearchLocationAddressRequest
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler

class SearchLocationRepositoryImpl(
    private val api: SearchLocationApi
) : SearchLocationRepository, NetworkResultHandler {

    override suspend fun searchLocationAddress(
        data: SearchLocationAddressRequest
    ): NetworkResult<SearchLocationResult> {
        val response = api.searchLocationAddress(query = data.query)
        return if (response.isSuccessful) {
            NetworkResult.Success(response.code(), "success", response.body())
        } else {
            NetworkResult.Error(response.code(), "error")
        }
    }
}