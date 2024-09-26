package com.onmyway.domain.usecase.datastore

import com.onmyway.domain.repository.DataStoreRepository

class SaveAccessTokenUseCase(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(
        accessToken: String
    ) {
        repository.saveAccessToken(accessToken)
    }
}