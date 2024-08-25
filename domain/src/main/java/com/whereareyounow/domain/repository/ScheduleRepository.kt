package com.whereareyounow.domain.repository

import com.whereareyounow.domain.entity.schedule.DailyScheduleInfo
import com.whereareyounow.domain.entity.schedule.DetailScheduleInfo
import com.whereareyounow.domain.entity.schedule.MonthlyScheduleInfo
import com.whereareyounow.domain.entity.schedule.ScheduleSeq
import com.whereareyounow.domain.request.schedule.AcceptScheduleRequestRequest
import com.whereareyounow.domain.request.schedule.CreateNewScheduleRequest
import com.whereareyounow.domain.request.schedule.DeleteScheduleRequest
import com.whereareyounow.domain.request.schedule.GetDailyScheduleRequest
import com.whereareyounow.domain.request.schedule.GetDetailScheduleRequest
import com.whereareyounow.domain.request.schedule.GetMonthlyScheduleRequest
import com.whereareyounow.domain.request.schedule.ModifyScheduleInfoRequest
import com.whereareyounow.domain.util.NetworkResult

interface ScheduleRepository {

    suspend fun getDetailSchedule(
        data: GetDetailScheduleRequest
    ): NetworkResult<DetailScheduleInfo>

    suspend fun modifyScheduleInfo(
        data: ModifyScheduleInfoRequest
    ): NetworkResult<ScheduleSeq>

    suspend fun createNewSchedule(
        data: CreateNewScheduleRequest
    ): NetworkResult<ScheduleSeq>

    suspend fun acceptScheduleRequest(
        data: AcceptScheduleRequestRequest
    ): NetworkResult<Unit>

    suspend fun getMonthlySchedule(
        data: GetMonthlyScheduleRequest
    ): NetworkResult<MonthlyScheduleInfo>

    suspend fun getDailySchedule(
        data: GetDailyScheduleRequest
    ): NetworkResult<DailyScheduleInfo>

    suspend fun deleteScheduleByInvitor(
        data: DeleteScheduleRequest
    ): NetworkResult<Unit>

    suspend fun deleteScheduleByCreator(
        data: DeleteScheduleRequest
    ): NetworkResult<Unit>
}