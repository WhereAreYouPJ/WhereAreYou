package com.onmyway.domain.usecase.datastore

import com.onmyway.domain.repository.DataStoreRepository

class GetRefreshTokenUseCase(
    private val repository: DataStoreRepository
) {
    operator fun invoke() = repository.getRefreshToken()
}