package com.whereareyou.repository

import com.whereareyou.datasource.RemoteDataSource
import com.whereareyou.domain.entity.apimessage.location.GetUserLocationResponse
import com.whereareyou.domain.entity.apimessage.location.SendUserLocationRequest
import com.whereareyou.domain.repository.LocationRepository
import com.whereareyou.domain.util.NetworkResult
import com.whereareyou.util.NetworkResultHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationRepositoryImpl(
    private val dataSource: RemoteDataSource
) : LocationRepository, NetworkResultHandler {

    /**
     * 사용자 실시간 위도 경도
     * implements [LocationRepository.getUserLocation]
     */
    override suspend fun getUserLocation(
        token: String,
        memberId: String
    ): NetworkResult<GetUserLocationResponse> {
        return withContext(Dispatchers.IO) {
            val response = dataSource.getUserLocation(token, memberId)
            handleResult(response) { it }
        }
    }

    /**
     * 사용자 위도 경도
     * implements [LocationRepository.sendUserLocation]
     */
    override suspend fun sendUserLocation(
        token: String,
        body: SendUserLocationRequest
    ): NetworkResult<Boolean> {
        return withContext(Dispatchers.IO) {
            val response = dataSource.sendUserLocation(token, body)
            handleResult(response) { it }
        }
    }
}