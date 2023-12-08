package com.whereareyounow.domain.usecase.signin

import com.whereareyounow.domain.repository.SignInRepository

class SaveMemberIdUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        memberId: String
    ) {
        repository.saveMemberId(memberId)
    }
}