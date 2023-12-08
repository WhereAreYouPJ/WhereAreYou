package com.whereareyounow.domain.usecase.signin

import com.whereareyounow.domain.entity.apimessage.signin.ReissueTokenRequest
import com.whereareyounow.domain.entity.apimessage.signin.ReissueTokenResponse
import com.whereareyounow.domain.repository.SignInRepository
import com.whereareyounow.domain.util.NetworkResult

class ReissueTokenUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        body: ReissueTokenRequest
    ): NetworkResult<ReissueTokenResponse> {
        return repository.reissueToken(body)
    }
}