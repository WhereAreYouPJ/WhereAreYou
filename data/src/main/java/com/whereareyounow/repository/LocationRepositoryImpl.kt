package com.whereareyounow.repository

import com.whereareyounow.api.LocationApi
import com.whereareyounow.domain.entity.UserLocation
import com.whereareyounow.domain.entity.location.LocationFavoriteInfo
import com.whereareyounow.domain.repository.LocationRepository
import com.whereareyounow.domain.request.location.DeleteFavoriteLocationRequest
import com.whereareyounow.domain.request.location.GetUserLocationRequest
import com.whereareyounow.domain.request.location.SendUserLocationRequest
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler

class LocationRepositoryImpl(
    private val locationApi: LocationApi
) : LocationRepository, NetworkResultHandler {
    override suspend fun getUserLocation(data: GetUserLocationRequest): NetworkResult<UserLocation> {
        return handleResult { locationApi.getUserLocation(data.memberSeq) }
    }

    override suspend fun sendUserLocation(data: SendUserLocationRequest): NetworkResult<String> {
        return  handleResult { locationApi.sendUserLocation(data) }
    }

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