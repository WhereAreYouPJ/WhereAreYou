package com.whereareyou.domain.usecase.signin

import com.whereareyou.domain.repository.SignInRepository
import kotlinx.coroutines.flow.Flow

class GetMemberIdUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(): Flow<String> {
        return repository.getMemberId()
    }
}