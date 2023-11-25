package com.whereareyou.repository

import android.util.Log
import com.whereareyou.domain.entity.apimessage.schedule.AddNewScheduleRequest
import com.whereareyou.datasource.RemoteDataSource
import com.whereareyou.domain.entity.apimessage.schedule.AcceptScheduleRequest
import com.whereareyou.domain.entity.apimessage.schedule.AddNewScheduleResponse
import com.whereareyou.domain.entity.apimessage.schedule.CheckArrivalRequest
import com.whereareyou.domain.entity.apimessage.schedule.DeleteScheduleRequest
import com.whereareyou.domain.entity.apimessage.schedule.EndScheduleRequest
import com.whereareyou.domain.entity.apimessage.schedule.GetDailyBriefScheduleResponse
import com.whereareyou.domain.entity.apimessage.schedule.GetDetailScheduleResponse
import com.whereareyou.domain.entity.apimessage.schedule.GetMonthlyScheduleResponse
import com.whereareyou.domain.entity.apimessage.schedule.ModifyScheduleMemberRequest
import com.whereareyou.domain.entity.apimessage.schedule.ModifyScheduleRequest
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

    /**
     * 월별 일정 정보
     * implements [ScheduleRepository.getMonthlySchedule]
     */
    override suspend fun getMonthlySchedule(
        token: String,
        memberId: String,
        year: Int,
        month: Int
    ): NetworkResult<GetMonthlyScheduleResponse> {
        return withContext(Dispatchers.IO) {
            val response = dataSource.getMonthlySchedule(token, memberId, year, month)
            handleResult(response) { it }
        }
    }

    /**
     * 일별 간략 정보
     * implements [ScheduleRepository.getDailyBriefSchedule]
     */
    override suspend fun getDailyBriefSchedule(
        token: String,
        memberId: String,
        year: Int,
        month: Int,
        date: Int
    ): NetworkResult<GetDailyBriefScheduleResponse> {
        return withContext(Dispatchers.IO) {
            val response = dataSource.getDailyBriefSchedule(token, memberId, year, month, date)
            handleResult(response) { it }
        }
    }

    /**
     * 일별 일정 상세 정보
     * implements [ScheduleRepository.getDetailSchedule]
     */
    override suspend fun getDetailSchedule(
        token: String,
        memberId: String,
        scheduleId: String
    ): NetworkResult<GetDetailScheduleResponse> {
        return withContext(Dispatchers.IO) {
            val response = dataSource.getDetailSchedule(token, memberId, scheduleId)
            handleResult(response) { it }
        }
    }

    /**
     * 일정 추가
     * implements [ScheduleRepository.addNewSchedule]
     */
    override suspend fun addNewSchedule(
        token: String,
        body: AddNewScheduleRequest
    ): NetworkResult<AddNewScheduleResponse> {
        return withContext(Dispatchers.IO) {
            val response = dataSource.addNewSchedule(token, body)
            handleResult(response) { it }
        }
    }

    /**
     * 일정 내용 수정
     * implements [ScheduleRepository.modifySchedule]
     */
    override suspend fun modifySchedule(
        token: String,
        body: ModifyScheduleRequest
    ): NetworkResult<Unit> {
        return withContext(Dispatchers.IO) {
            val response = dataSource.modifySchedule(token, body)
            handleResult(response) { it }
        }
    }

    /**
     * 일정 멤버 수정
     * implements [ScheduleRepository.modifyScheduleMember]
     */
    override suspend fun modifyScheduleMember(
        token: String,
        body: ModifyScheduleMemberRequest
    ): NetworkResult<Unit> {
        return withContext(Dispatchers.IO) {
            val response = dataSource.modifyScheduleMember(token, body)
            handleResult(response) { it }
        }
    }

    /**
     * 일정 삭제
     * implements [ScheduleRepository.deleteSchedule]
     */
    override suspend fun deleteSchedule(
        token: String,
        body: DeleteScheduleRequest
    ): NetworkResult<Unit> {
        return withContext(Dispatchers.IO) {
            val response = dataSource.deleteSchedule(token, body)
            handleResult(response) { it }
        }
    }

    /**
     * 일정 수락
     * implements [ScheduleRepository.acceptSchedule]
     */
    override suspend fun acceptSchedule(
        token: String,
        body: AcceptScheduleRequest
    ): NetworkResult<Boolean> {
        return withContext(Dispatchers.IO) {
            val response = dataSource.acceptSchedule(token, body)
            handleResult(response) { it }
        }
    }

    /**
     * 일정 종료
     * implements [ScheduleRepository.endSchedule]
     */
    override suspend fun endSchedule(
        token: String,
        body: EndScheduleRequest
    ): NetworkResult<Boolean> {
        return withContext(Dispatchers.IO) {
            val response = dataSource.endSchedule(token, body)
            handleResult(response) { it }
        }
    }

    /**
     * 도착 여부
     * implements [ScheduleRepository.checkArrival]
     */
    override suspend fun checkArrival(
        token: String,
        body: CheckArrivalRequest
    ): NetworkResult<Boolean> {
        return withContext(Dispatchers.IO) {
            val response = dataSource.checkArrival(token, body)
            handleResult(response) { it }
        }
    }
}