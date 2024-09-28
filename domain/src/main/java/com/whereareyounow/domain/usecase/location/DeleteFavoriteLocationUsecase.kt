package com.whereareyounow.domain.usecase.location

import com.whereareyounow.domain.repository.LocationRepository
import kotlinx.coroutines.flow.flow

class DeleteFavoriteLocationUsecase(
    private val repository: LocationRepository
) {
    operator fun invoke(
        memberSeq : Int,
        locationSeqs : List<Int>
    ) = flow {
        val response = repository.deleteFavoriteLocation(
            memberSeq = memberSeq,
            locationSeqs = locationSeqs
        )
        emit(response)
    }
}

