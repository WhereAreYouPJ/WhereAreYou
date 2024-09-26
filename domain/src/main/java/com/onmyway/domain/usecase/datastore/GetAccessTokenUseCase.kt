package com.onmyway.domain.usecase.datastore

import com.onmyway.domain.repository.DataStoreRepository

class GetAccessTokenUseCase(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke() = repository.getAccessToken()
}