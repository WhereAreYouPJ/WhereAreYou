package com.onmyway.domain.usecase.datastore

import com.onmyway.domain.repository.DataStoreRepository

class ClearAuthDataStoreUseCase(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke() = repository.clearAll()
}