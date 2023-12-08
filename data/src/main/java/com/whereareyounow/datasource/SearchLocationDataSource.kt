package com.whereareyounow.datasource

import com.whereareyounow.api.SearchLocationApi
import retrofit2.Response

class SearchLocationDataSource(
    private val api: SearchLocationApi
) {
    suspend fun getLocationAddress(
        query: String,
    ): Response<com.whereareyounow.domain.entity.apimessage.schedule.GetLocationAddressResponse> {
        return api.getLocationAddress(
                query = query
            )
    }
}