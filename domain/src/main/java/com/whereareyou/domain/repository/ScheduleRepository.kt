package com.whereareyou.domain.repository

import com.whereareyou.domain.entity.schedule.BriefSchedule
import com.whereareyou.domain.entity.schedule.DetailSchedule
import com.whereareyou.domain.util.NetworkResult
import com.whereareyou.domain.entity.schedule.ScheduleCountByDay

interface ScheduleRepository {

    suspend fun getMonthlySchedule(
        token: String,
        memberId: String,
        year: Int,
        month: Int
    ): NetworkResult<List<ScheduleCountByDay>>

    suspend fun getDailyBriefSchedule(
        token: String,
        memberId: String,
        year: Int,
        month: Int,
        date: Int
    ): NetworkResult<List<BriefSchedule>>

    suspend fun getDetailSchedule(
        token: String,
        memberId: String,
        scheduleId: String
    ): NetworkResult<DetailSchedule>

    suspend fun addNewSchedule(
        token: String,
        memberId: String,
        start: String,
        end: String,
        title: String,
        place: String,
        momo: String,
        memberIdList: List<String>
    ): NetworkResult<String>
}