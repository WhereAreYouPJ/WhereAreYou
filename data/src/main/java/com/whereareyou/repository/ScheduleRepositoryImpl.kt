package com.whereareyou.repository

import android.util.Log
import com.whereareyou.datasource.RemoteDataSource
import com.whereareyou.domain.entity.schedule.BriefSchedule
import com.whereareyou.domain.entity.schedule.DetailSchedule
import com.whereareyou.domain.entity.schedule.ScheduleCountByDay
import com.whereareyou.domain.repository.ScheduleRepository
import com.whereareyou.domain.util.NetworkResult
import com.whereareyou.util.NetworkResultHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScheduleRepositoryImpl(
    private val dataSource: RemoteDataSource
) : ScheduleRepository, NetworkResultHandler {

    override suspend fun getMonthlySchedule(
        token: String,
        memberId: String,
        year: Int,
        month: Int
    ): NetworkResult<List<ScheduleCountByDay>> {
        val response = dataSource.getMonthlySchedule(token, memberId, year, month)
        Log.e("getMonthlySchedule", "getMonthlySchedule")
        return withContext(Dispatchers.IO) {
            handleResult(response) { body ->
                body.schedules
            }
        }
    }

    override suspend fun getDailyBriefSchedule(
        token: String,
        memberId: String,
        year: Int,
        month: Int,
        date: Int
    ): NetworkResult<List<BriefSchedule>> {
        val response = dataSource.getDailyBriefSchedule(token, memberId, year, month, date)
        return withContext(Dispatchers.IO) {
            handleResult(response) { body ->
                body.schedules
            }
        }
    }

    override suspend fun getDetailSchedule(
        token: String,
        memberId: String,
        scheduleId: String
    ): NetworkResult<DetailSchedule> {
        val response = dataSource.getDetailSchedule(token, memberId, scheduleId)
        return withContext(Dispatchers.IO) {
            handleResult(response) { body ->
                DetailSchedule(body.start, body.end, body.title, body.place, body.memo, body.friendsIdList) }
        }
    }

    override suspend fun addNewSchedule(
        token: String,
        memberId: String,
        start: String,
        end: String,
        title: String,
        place: String,
        momo: String,
        memberIdList: List<String>
    ): NetworkResult<String> {
        TODO("Not yet implemented")
    }
}