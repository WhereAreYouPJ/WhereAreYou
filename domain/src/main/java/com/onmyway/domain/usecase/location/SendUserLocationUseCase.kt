package com.onmyway.domain.usecase.location

import com.onmyway.domain.repository.LocationRepository

class SendUserLocationUseCase(
    private val repository: LocationRepository
) {
//    suspend operator fun invoke(
//        token: String,
//        body: com.whereareyounow.domain.request.location.SendUserLocationRequest
//    ): NetworkResult<Boolean> {
//        return repository.sendUserLocation(token, body)
//    }
}