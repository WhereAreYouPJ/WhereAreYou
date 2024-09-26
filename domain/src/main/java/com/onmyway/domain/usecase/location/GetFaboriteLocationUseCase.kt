package com.onmyway.domain.usecase.location

import com.onmyway.domain.repository.LocationRepository
import kotlinx.coroutines.flow.flow

class GetFaboriteLocationUseCase(
    private val repository: LocationRepository

) {
    operator fun invoke(
        memberSeq : Int
    ) = flow {
        val response = repository.getLocationFaborite(memberSeq)
        emit(response)
    }
}