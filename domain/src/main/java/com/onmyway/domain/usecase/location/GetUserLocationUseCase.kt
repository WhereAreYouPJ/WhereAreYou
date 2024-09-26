package com.onmyway.domain.usecase.location

import com.onmyway.domain.repository.LocationRepository

class GetUserLocationUseCase(
    private val repository: LocationRepository
) {
    //    suspend operator fun invoke(
//        token: String,
//        body: com.whereareyounow.domain.request.location.GetUserLocationRequest
//    ): NetworkResult<List<com.whereareyounow.domain.request.location.UserLocation>> {
//        return repository.getUserLocation(token, body)
//    }
}