package com.whereareyounow.domain.usecase.datastore

import com.whereareyounow.domain.repository.DataStoreRepository

class GetMemberCodeUseCase(
    private val repository: DataStoreRepository
) {
    operator fun invoke() = repository.getMemberCode()
}