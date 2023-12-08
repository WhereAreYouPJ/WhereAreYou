package com.whereareyounow.domain.usecase.signin

import com.whereareyounow.domain.repository.SignInRepository
import kotlinx.coroutines.flow.Flow

class GetRefreshTokenUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(): Flow<String> {
        return repository.getRefreshToken()
    }
}