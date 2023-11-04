package com.whereareyou.domain.usecase.signin

import com.whereareyou.domain.repository.SignInRepository

class SaveMemberIdUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        memberId: String
    ) {
        repository.saveMemberId(memberId)
    }
}