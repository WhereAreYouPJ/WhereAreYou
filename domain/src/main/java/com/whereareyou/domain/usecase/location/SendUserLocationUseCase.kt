package com.whereareyou.domain.usecase.location

import com.whereareyou.domain.entity.apimessage.location.SendUserLocationRequest
import com.whereareyou.domain.repository.LocationRepository
import com.whereareyou.domain.util.NetworkResult

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