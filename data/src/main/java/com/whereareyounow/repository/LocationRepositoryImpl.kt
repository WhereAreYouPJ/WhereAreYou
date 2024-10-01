package com.whereareyounow.repository

import com.whereareyounow.api.LocationApi
import com.whereareyounow.domain.entity.location.LocationFavoriteInfo
import com.whereareyounow.domain.repository.LocationRepository
import com.whereareyounow.domain.request.location.DeleteFavoriteLocationRequest
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler

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
    override suspend fun getLocationFavorite(
        memberSeq: Int
    ): NetworkResult<List<LocationFavoriteInfo>> {
        return handleResult { locationApi.getLocationFaborite(
            memberSeq = memberSeq
        ) }
    }

    override suspend fun deleteFavoriteLocation(
        memberSeq: Int,
        locationSeqs : List<Int>
    ): NetworkResult<List<LocationFavoriteInfo>> {
        return handleResult { locationApi.deleteFavoriteLocation(
            deleteFavoriteLocationRequest = DeleteFavoriteLocationRequest(
                memberSeq = memberSeq,
                locationSeqs = locationSeqs
            )
        ) }
    }
}