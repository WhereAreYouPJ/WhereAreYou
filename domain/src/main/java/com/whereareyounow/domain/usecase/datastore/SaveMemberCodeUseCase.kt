package com.whereareyounow.domain.usecase.datastore

import com.whereareyounow.domain.repository.DataStoreRepository

class SaveMemberCodeUseCase(
    private val repository: DataStoreRepository
) {
    operator fun invoke(
        memberCode: String
    ) = repository.saveMemberCode(memberCode)
}