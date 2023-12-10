package com.whereareyounow.domain.usecase.fcm

import com.whereareyounow.domain.entity.apimessage.fcm.UpdateFCMTokenRequest
import com.whereareyounow.domain.repository.FCMRepository
import com.whereareyounow.domain.util.NetworkResult

class UpdateFCMTokenUseCase(
    private val repository: FCMRepository
) {
    suspend operator fun invoke(
        token: String,
        body: UpdateFCMTokenRequest
    ): NetworkResult<Unit> {
        return repository.updateFCMToken(token, body)
    }
}