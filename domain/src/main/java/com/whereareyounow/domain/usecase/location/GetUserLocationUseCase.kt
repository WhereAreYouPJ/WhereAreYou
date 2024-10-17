package com.whereareyounow.domain.usecase.location

import com.whereareyounow.domain.repository.LocationRepository
import com.whereareyounow.domain.request.location.GetUserLocationRequest
import kotlinx.coroutines.flow.flow

class GetUserLocationUseCase(
    private val repository: LocationRepository
) {
    operator fun invoke(
        data: GetUserLocationRequest
    ) = flow {
        val response = repository.getUserLocation(data)
        emit(response)
    }
}