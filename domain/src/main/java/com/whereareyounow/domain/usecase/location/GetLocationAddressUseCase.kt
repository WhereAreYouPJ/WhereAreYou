package com.whereareyounow.domain.usecase.location

import com.whereareyounow.domain.entity.apimessage.schedule.GetLocationAddressResponse
import com.whereareyounow.domain.repository.SearchLocationRepository
import com.whereareyounow.domain.util.NetworkResult

class GetLocationAddressUseCase(
    private val repository: SearchLocationRepository
) {
    suspend operator fun invoke(
        query: String
    ): NetworkResult<GetLocationAddressResponse> {
        return repository.getLocationAddress(query)
    }
}