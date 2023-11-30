package com.whereareyou.domain.usecase.signin

import com.whereareyou.domain.entity.apimessage.signin.ModifyMyInfoRequest
import com.whereareyou.domain.repository.SignInRepository
import com.whereareyou.domain.util.NetworkResult

class ModifyMyInfoUseCase(
    private val repository: SignInRepository
) {
    suspend operator fun invoke(
        token: String,
        body: ModifyMyInfoRequest
    ): NetworkResult<Unit> {
        return repository.modifyMyInfo(token, body)
    }
}