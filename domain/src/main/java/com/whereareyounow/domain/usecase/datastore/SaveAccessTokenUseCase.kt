package com.whereareyounow.domain.usecase.datastore

import com.whereareyounow.domain.repository.DataStoreRepository

class SaveAccessTokenUseCase(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(
        accessToken: String
    ) {
        repository.saveAccessToken(accessToken)
    }
}