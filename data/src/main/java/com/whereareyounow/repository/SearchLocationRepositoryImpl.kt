package com.whereareyounow.repository

import com.whereareyounow.datasource.SearchLocationDataSource
import com.whereareyounow.domain.repository.SearchLocationRepository
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler

class SearchLocationRepositoryImpl(
    private val dataSource: SearchLocationDataSource
) : SearchLocationRepository, NetworkResultHandler {

//    override suspend fun getLocationAddress(
//        query: String
//    ): NetworkResult<com.whereareyounow.domain.request.schedule.GetLocationAddressResponse> {
//        return handleResult { dataSource.getLocationAddress(query) }
//    }
}