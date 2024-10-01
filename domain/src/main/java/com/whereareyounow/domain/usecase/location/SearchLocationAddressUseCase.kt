package com.whereareyounow.domain.usecase.location

import com.whereareyounow.domain.repository.SearchLocationRepository
import com.whereareyounow.domain.request.location.SearchLocationAddressRequest
import kotlinx.coroutines.flow.flow

class SearchLocationAddressUseCase(
    private val repository: SearchLocationRepository
) {
    operator fun invoke(
        data: SearchLocationAddressRequest
    ) = flow {
        val response = repository.searchLocationAddress(data)
        emit(response)
    }
}