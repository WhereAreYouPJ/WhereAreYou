package com.whereareyounow.repository

import com.whereareyounow.datasource.RemoteDataSource
import com.whereareyounow.domain.entity.apimessage.fcm.DeleteFCMTokenRequest
import com.whereareyounow.domain.entity.apimessage.fcm.UpdateFCMTokenRequest
import com.whereareyounow.domain.repository.FCMRepository
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler

class FCMRepositoryImpl(
    private val dataSource: RemoteDataSource
) : FCMRepository, NetworkResultHandler {

    /**
     * FCM 토큰 저장 및 갱신
     * [FCMRepository.updateFCMToken]
     */
    override suspend fun updateFCMToken(
        token: String,
        body: UpdateFCMTokenRequest
    ): NetworkResult<Unit> {
        return handleResult { dataSource.updateFCMToken(token, body) }
    }

    /**
     * FCM 토큰 삭제(로그아웃)
     * [FCMRepository.deleteFCMToken]
     */
    override suspend fun deleteFCMToken(
        token: String,
        body: DeleteFCMTokenRequest
    ): NetworkResult<Unit> {
        return handleResult { dataSource.deleteFCMToken(token, body) }
    }
}