package com.whereareyou.domain.usecase.location

import com.whereareyou.domain.entity.schedule.LocationInformation
import com.whereareyou.domain.repository.SearchLocationRepository
import com.whereareyou.domain.util.NetworkResult

class GetLocationAddressUseCase(
    private val repository: SearchLocationRepository
) {
    suspend fun getLocationAddress(
        query: String
    ): NetworkResult<List<LocationInformation>> {
        return repository.getLocationAddress(query)
    }
}