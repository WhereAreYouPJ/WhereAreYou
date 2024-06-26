package com.whereareyounow.domain.usecase.location

import com.whereareyounow.domain.entity.apimessage.location.SendUserLocationRequest
import com.whereareyounow.domain.repository.LocationRepository
import com.whereareyounow.domain.util.NetworkResult

class SendUserLocationUseCase(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(
        token: String,
        body: SendUserLocationRequest
    ): NetworkResult<Boolean> {
        return repository.sendUserLocation(token, body)
    }
}