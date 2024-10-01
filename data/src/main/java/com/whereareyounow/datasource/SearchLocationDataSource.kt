package com.whereareyounow.datasource

import com.whereareyounow.api.SearchLocationApi

class SearchLocationDataSource(
    private val api: SearchLocationApi
) {
//    suspend fun getLocationAddress(
//        query: String,
//    ): Response<com.whereareyounow.domain.request.schedule.GetLocationAddressResponse> {
//        return api.getLocationAddress(
//                query = query
//            )
//    }
}