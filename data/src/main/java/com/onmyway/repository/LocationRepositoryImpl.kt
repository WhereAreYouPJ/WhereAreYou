package com.onmyway.repository

import com.onmyway.api.LocationApi
import com.onmyway.domain.entity.location.LocationFaboriteInfo
import com.onmyway.domain.repository.LocationRepository
import com.onmyway.domain.util.NetworkResult
import com.onmyway.util.NetworkResultHandler

class LocationRepositoryImpl(
    private val locationApi: LocationApi
) : LocationRepository, NetworkResultHandler {

    //    /**
//     * 사용자 실시간 위도 경도
//     * implements [LocationRepository.getUserLocation]
//     */
//    override suspend fun getUserLocation(
//        token: String,
//        body: com.whereareyounow.domain.request.location.GetUserLocationRequest
//    ): NetworkResult<List<com.whereareyounow.domain.request.location.UserLocation>> {
//        return handleResult { dataSource.getUserLocation(token, body) }
//    }
//
//    /**
//     * 사용자 위도 경도
//     * implements [LocationRepository.sendUserLocation]
//     */
//    override suspend fun sendUserLocation(
//        token: String,
//        body: com.whereareyounow.domain.request.location.SendUserLocationRequest
//    ): NetworkResult<Boolean> {
//        return handleResult { dataSource.sendUserLocation(token, body) }
//    }
    override suspend fun getLocationFaborite(
        memberSeq: Int
    ): NetworkResult<LocationFaboriteInfo> {
        return handleResult { locationApi.getLocationFaborite(
            memberSeq = memberSeq
        ) }
    }
}