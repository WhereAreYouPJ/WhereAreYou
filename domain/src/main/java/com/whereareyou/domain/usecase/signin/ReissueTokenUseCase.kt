package com.whereareyou.domain.usecase.signin

import com.whereareyou.domain.entity.apimessage.signin.ReissueTokenRequest
import com.whereareyou.domain.entity.apimessage.signin.ReissueTokenResponse
import com.whereareyou.domain.repository.SignInRepository
import com.whereareyou.domain.util.NetworkResult

class ReissueTokenUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        body: ReissueTokenRequest
    ): NetworkResult<ReissueTokenResponse> {
        return repository.reissueToken(body)
    }
}