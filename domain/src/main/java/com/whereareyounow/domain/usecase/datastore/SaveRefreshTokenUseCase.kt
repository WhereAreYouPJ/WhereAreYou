package com.whereareyounow.domain.usecase.datastore

import com.whereareyounow.domain.repository.DataStoreRepository

class SaveRefreshTokenUseCase(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(
        refreshToken: String
    ) = repository.saveRefreshToken(refreshToken)
}