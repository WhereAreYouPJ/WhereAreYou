package com.whereareyounow.domain.usecase.fcm

import com.whereareyounow.domain.entity.apimessage.fcm.DeleteFCMTokenRequest
import com.whereareyounow.domain.repository.FCMRepository
import com.whereareyounow.domain.util.NetworkResult

class DeleteFCMTokenUseCase(
    private val repository: FCMRepository
) {
    suspend operator fun invoke(
        token: String,
        body: DeleteFCMTokenRequest
    ): NetworkResult<Unit> {
        return repository.deleteFCMToken(token, body)
    }
}