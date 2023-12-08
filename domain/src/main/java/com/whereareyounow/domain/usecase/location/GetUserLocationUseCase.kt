package com.whereareyounow.domain.usecase.location

import com.whereareyounow.domain.entity.apimessage.location.GetUserLocationRequest
import com.whereareyounow.domain.entity.apimessage.location.UserLocation
import com.whereareyounow.domain.repository.LocationRepository
import com.whereareyounow.domain.util.NetworkResult

class GetUserLocationUseCase(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(
        token: String,
        body: GetUserLocationRequest
    ): NetworkResult<List<UserLocation>> {
        return repository.getUserLocation(token, body)
    }
}