package com.whereareyounow.domain.usecase.location

import com.whereareyounow.domain.repository.LocationRepository
import com.whereareyounow.domain.request.member.CheckEmailDuplicateRequest
import com.whereareyounow.domain.util.NetworkResult
import kotlinx.coroutines.flow.flow

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