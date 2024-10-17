package com.whereareyounow.domain.usecase.location

import com.whereareyounow.domain.repository.LocationRepository
import com.whereareyounow.domain.request.location.SendUserLocationRequest
import kotlinx.coroutines.flow.flow

class SendUserLocationUseCase(
    private val repository: LocationRepository
) {
    operator fun invoke(
        data: SendUserLocationRequest
    ) = flow {
        val response = repository.sendUserLocation(data)
        emit(response)
    }
}