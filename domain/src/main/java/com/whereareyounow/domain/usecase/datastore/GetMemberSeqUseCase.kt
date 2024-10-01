package com.whereareyounow.domain.usecase.datastore

import com.whereareyounow.domain.repository.DataStoreRepository
import com.whereareyounow.domain.repository.SearchLocationRepository

class GetMemberSeqUseCase(
    private val repository: DataStoreRepository
) {
    operator fun invoke() = repository.getMemberSeq()
}