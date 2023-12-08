package com.whereareyounow.repository

import com.whereareyounow.datasource.SearchLocationDataSource
import com.whereareyounow.domain.entity.apimessage.schedule.GetLocationAddressResponse
import com.whereareyounow.domain.repository.SearchLocationRepository
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler

class SearchLocationRepositoryImpl(
    private val dataSource: SearchLocationDataSource
) : SearchLocationRepository, NetworkResultHandler {

    override suspend fun getLocationAddress(
        query: String
    ): NetworkResult<GetLocationAddressResponse> {
        return handleResult { dataSource.getLocationAddress(query) }
    }
}