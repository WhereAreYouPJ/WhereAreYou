package com.whereareyounow.domain.usecase.datastore

import com.whereareyounow.domain.repository.DataStoreRepository

class GetAccessTokenUseCase(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke() = repository.getAccessToken()
}