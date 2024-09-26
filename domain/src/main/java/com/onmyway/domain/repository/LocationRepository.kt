package com.onmyway.domain.repository

import com.onmyway.domain.entity.location.LocationFaboriteInfo
import com.onmyway.domain.util.NetworkResult

interface LocationRepository {

//    // 사용자 실시간 위도 경도
//    suspend fun getUserLocation(
//        token: String,
//        body: com.whereareyounow.domain.request.location.GetUserLocationRequest
//    ): NetworkResult<List<com.whereareyounow.domain.request.location.UserLocation>>
//
//    // 사용자 위도 경도
//    suspend fun sendUserLocation(
//        token: String,
//        body: com.whereareyounow.domain.request.location.SendUserLocationRequest
//    ): NetworkResult<Boolean>

    // 즐겨찾기 조회
    suspend fun getLocationFaborite(
        memberSeq : Int
    ): NetworkResult<LocationFaboriteInfo>


}