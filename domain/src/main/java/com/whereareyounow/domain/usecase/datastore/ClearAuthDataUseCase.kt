package com.whereareyounow.domain.usecase.datastore

import com.whereareyounow.domain.repository.DataStoreRepository

class ClearAuthDataStoreUseCase(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke() = repository.clearAll()
}