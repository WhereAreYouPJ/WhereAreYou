package com.whereareyou.datasource

import com.whereareyou.api.SearchLocationApi
import com.whereareyou.apimessage.schedule.GetLocationAddressResponse
import retrofit2.Response

class SearchLocationDataSource(
    private val api: SearchLocationApi
) {
    suspend fun getLocationAddress(
        query: String,
    ): Response<GetLocationAddressResponse> {
        return api.getLocationAddress(
                query = query
            )
    }
}