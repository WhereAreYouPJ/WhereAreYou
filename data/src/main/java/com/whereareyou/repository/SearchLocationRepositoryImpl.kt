package com.whereareyou.repository

import com.whereareyou.datasource.SearchLocationDataSource
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
    ): NetworkResult<List<LocationInformation>> {
        val response = dataSource.getLocationAddress(query)
        return withContext(Dispatchers.IO) {
            handleResult(response) { body ->
                body.items
            }
        }
    }
}