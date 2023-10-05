package com.whereareyou.repository

import com.whereareyou.model.MonthlySchedule
import com.whereareyou.datasource.RemoteDataSource
import com.whereareyou.model.DailySchedule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RemoteRepository(
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun getMonthlySchedule(
        memberId: String,
        year: Int,
        month: Int
    ): List<MonthlySchedule> {
        return withContext(Dispatchers.IO) {
            remoteDataSource.getMonthlySchedule(memberId, year, month)?.schedules ?: emptyList()
        }
    }

    suspend fun getDailySchedule(
        memberId: String,
        year: Int,
        month: Int,
        date: Int
    ): List<DailySchedule> {
        return withContext(Dispatchers.IO) {
            remoteDataSource.getDailySchedule(memberId, year, month, date)?.schedules ?: emptyList()
        }
    }
}