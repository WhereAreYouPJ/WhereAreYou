package com.whereareyounow.domain.usecase.datastore

import com.whereareyounow.domain.repository.DataStoreRepository

class GetRefreshTokenUseCase(
    private val repository: DataStoreRepository
) {
    operator fun invoke() = repository.getRefreshToken()
}