package com.whereareyou.domain.usecase.location

import com.whereareyou.domain.entity.apimessage.location.GetUserLocationResponse
import com.whereareyou.domain.repository.LocationRepository
import com.whereareyou.domain.util.NetworkResult

class GetUserLocationUseCase(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(
        token: String,
        memberId: String
    ): NetworkResult<GetUserLocationResponse> {
        return repository.getUserLocation(token, memberId)
    }
}