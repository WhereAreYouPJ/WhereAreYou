package com.whereareyou.domain.repository

import com.whereareyou.domain.entity.apimessage.location.GetUserLocationRequest
import com.whereareyou.domain.entity.apimessage.location.GetUserLocationResponse
import com.whereareyou.domain.entity.apimessage.location.SendUserLocationRequest
import com.whereareyou.domain.entity.apimessage.location.UserLocation
import com.whereareyou.domain.util.NetworkResult

interface LocationRepository {

    // 사용자 실시간 위도 경도
    suspend fun getUserLocation(
        token: String,
        body: GetUserLocationRequest
    ): NetworkResult<List<UserLocation>>

    // 사용자 위도 경도
    suspend fun sendUserLocation(
        token: String,
        body: SendUserLocationRequest
    ): NetworkResult<Boolean>
}