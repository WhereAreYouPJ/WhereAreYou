package com.onmyway.repository

import com.onmyway.datasource.SearchLocationDataSource
import com.onmyway.domain.repository.SearchLocationRepository
import com.onmyway.util.NetworkResultHandler

class SearchLocationRepositoryImpl(
    private val dataSource: SearchLocationDataSource
) : SearchLocationRepository, NetworkResultHandler {

//    override suspend fun getLocationAddress(
//        query: String
//    ): NetworkResult<com.whereareyounow.domain.request.schedule.GetLocationAddressResponse> {
//        return handleResult { dataSource.getLocationAddress(query) }
//    }
}