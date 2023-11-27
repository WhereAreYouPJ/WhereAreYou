package com.whereareyou.repository

import com.whereareyou.datasource.SearchLocationDataSource
import com.whereareyou.domain.entity.apimessage.schedule.GetLocationAddressResponse
import com.whereareyou.domain.entity.schedule.LocationInformation
import com.whereareyou.domain.repository.SearchLocationRepository
import com.whereareyou.domain.util.NetworkResult
import com.whereareyou.util.NetworkResultHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchLocationRepositoryImpl(
    private val dataSource: SearchLocationDataSource
) : SearchLocationRepository, NetworkResultHandler {

    override suspend fun getLocationAddress(
        query: String
    ): NetworkResult<GetLocationAddressResponse> {
        return handleResult { dataSource.getLocationAddress(query) }
    }
}