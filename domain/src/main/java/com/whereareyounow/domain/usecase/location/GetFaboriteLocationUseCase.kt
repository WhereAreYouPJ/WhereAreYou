package com.whereareyounow.domain.usecase.location

import com.whereareyounow.domain.repository.LocationRepository
import kotlinx.coroutines.flow.flow

class GetFavoriteLocationUseCase(
    private val repository: LocationRepository

) {
    operator fun invoke(
        memberSeq : Int
    ) = flow {
        val response = repository.getLocationFavorite(memberSeq)
        emit(response)
    }
}