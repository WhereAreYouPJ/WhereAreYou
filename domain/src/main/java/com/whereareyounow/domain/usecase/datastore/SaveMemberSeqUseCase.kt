package com.whereareyounow.domain.usecase.datastore

import com.whereareyounow.domain.repository.DataStoreRepository

class SaveMemberSeqUseCase(
    private val repository: DataStoreRepository
) {
    operator fun invoke(
        memberSeq: String
    ) = repository.saveMemberSeq(memberSeq)
}