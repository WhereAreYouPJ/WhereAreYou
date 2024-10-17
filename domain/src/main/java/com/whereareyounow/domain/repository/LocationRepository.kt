package com.whereareyounow.domain.repository

import com.whereareyounow.domain.entity.UserLocation
import com.whereareyounow.domain.entity.location.LocationFavoriteInfo
import com.whereareyounow.domain.request.location.GetUserLocationRequest
import com.whereareyounow.domain.request.location.SendUserLocationRequest
import com.whereareyounow.domain.util.NetworkResult

interface LocationRepository {

    suspend fun getUserLocation(
        data: GetUserLocationRequest
    ): NetworkResult<UserLocation>

    suspend fun sendUserLocation(
        data: SendUserLocationRequest
    ): NetworkResult<String>

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