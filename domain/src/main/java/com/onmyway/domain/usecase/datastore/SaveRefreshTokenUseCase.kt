package com.onmyway.domain.usecase.datastore

import com.onmyway.domain.repository.DataStoreRepository

class SaveRefreshTokenUseCase(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(
        refreshToken: String
    ) {
        repository.saveRefreshToken(refreshToken)
    }
}