package com.whereareyou.datasource

import com.whereareyou.api.SearchLocationApi
import com.whereareyou.domain.entity.apimessage.schedule.GetLocationAddressResponse
import retrofit2.Response

class SearchLocationDataSource(
    private val api: SearchLocationApi
) {
    suspend fun getLocationAddress(
        query: String,
    ): Response<com.whereareyou.domain.entity.apimessage.schedule.GetLocationAddressResponse> {
        return api.getLocationAddress(
                query = query
            )
    }
}