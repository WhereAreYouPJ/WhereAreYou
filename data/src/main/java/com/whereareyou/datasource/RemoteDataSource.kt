package com.whereareyou.datasource

import com.whereareyou.model.GetDailyScheduleResponse
import com.whereareyou.model.GetMonthlyScheduleResponse
import com.whereareyou.api.WhereAreYouApi

class RemoteDataSource(
    private val api: WhereAreYouApi
) {
    suspend fun getMonthlySchedule(
        memberId: String,
        year: Int,
        month: Int
    ): GetMonthlyScheduleResponse? {
        return try {
            api.getMonthlySchedule(
                memberId = memberId,
                year = year,
                month = month
            ).body()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getDailySchedule(
        memberId: String,
        year: Int,
        month: Int,
        date: Int
    ): GetDailyScheduleResponse? {
        return try {
            api.getDailySchedule(
                memberId = memberId,
                year = year,
                month = month,
                date = date
            ).body()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}