package com.whereareyounow.repository

import com.whereareyounow.datasource.RemoteDataSource
import com.whereareyounow.domain.entity.apimessage.location.GetUserLocationRequest
import com.whereareyounow.domain.entity.apimessage.location.SendUserLocationRequest
import com.whereareyounow.domain.entity.apimessage.location.UserLocation
import com.whereareyounow.domain.repository.LocationRepository
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler

class LocationRepositoryImpl(
    private val dataSource: RemoteDataSource
) : LocationRepository, NetworkResultHandler {

    /**
     * 사용자 실시간 위도 경도
     * implements [LocationRepository.getUserLocation]
     */
    override suspend fun getUserLocation(
        token: String,
        body: GetUserLocationRequest
    ): NetworkResult<List<UserLocation>> {
        return handleResult { dataSource.getUserLocation(token, body) }
    }

    /**
     * 사용자 위도 경도
     * implements [LocationRepository.sendUserLocation]
     */
    override suspend fun sendUserLocation(
        token: String,
        body: SendUserLocationRequest
    ): NetworkResult<Boolean> {
        return handleResult { dataSource.sendUserLocation(token, body) }
    }
}