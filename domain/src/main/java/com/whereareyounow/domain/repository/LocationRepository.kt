package com.whereareyounow.domain.repository

import com.whereareyounow.domain.entity.location.LocationFavoriteInfo
import com.whereareyounow.domain.util.NetworkResult

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

    // 위치 즐겨찾기 조회
    suspend fun getLocationFavorite(
        memberSeq : Int
    ): NetworkResult<List<LocationFavoriteInfo>>

    // 위치 즐겨찾기 삭제
    suspend fun deleteFavoriteLocation(
        memberSeq : Int,
        locationSeqs : List<Int>
    ): NetworkResult<List<LocationFavoriteInfo>>


}