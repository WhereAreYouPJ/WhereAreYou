package com.whereareyounow.repository

import com.whereareyounow.datasource.RemoteDataSource
import com.whereareyounow.domain.entity.apimessage.schedule.AcceptScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.AddNewScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.AddNewScheduleResponse
import com.whereareyounow.domain.entity.apimessage.schedule.CheckArrivalRequest
import com.whereareyounow.domain.entity.apimessage.schedule.DeleteScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.GetDailyBriefScheduleResponse
import com.whereareyounow.domain.entity.apimessage.schedule.GetDetailScheduleResponse
import com.whereareyounow.domain.entity.apimessage.schedule.GetMonthlyScheduleResponse
import com.whereareyounow.domain.entity.apimessage.schedule.GetScheduleInvitationResponse
import com.whereareyounow.domain.entity.apimessage.schedule.ModifyScheduleMemberRequest
import com.whereareyounow.domain.entity.apimessage.schedule.ModifyScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.RefuseOrQuitScheduleRequest
import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.NetworkResultHandler

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
        return handleResult { dataSource.getMonthlySchedule(token, memberId, year, month) }
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
        return handleResult { dataSource.getDailyBriefSchedule(token, memberId, year, month, date) }
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
        return handleResult { dataSource.getDetailSchedule(token, memberId, scheduleId) }
    }

    /**
     * 일정 추가
     * implements [ScheduleRepository.addNewSchedule]
     */
    override suspend fun addNewSchedule(
        token: String,
        body: AddNewScheduleRequest
    ): NetworkResult<AddNewScheduleResponse> {
        return handleResult { dataSource.addNewSchedule(token, body) }
    }

    /**
     * 일정 내용 수정
     * implements [ScheduleRepository.modifySchedule]
     */
    override suspend fun modifySchedule(
        token: String,
        body: ModifyScheduleRequest
    ): NetworkResult<Unit> {
        return handleResult { dataSource.modifySchedule(token, body) }
    }

    /**
     * 일정 멤버 수정
     * implements [ScheduleRepository.modifyScheduleMember]
     */
    override suspend fun modifyScheduleMember(
        token: String,
        body: ModifyScheduleMemberRequest
    ): NetworkResult<Unit> {
        return handleResult { dataSource.modifyScheduleMember(token, body) }
    }

    /**
     * 일정 삭제
     * implements [ScheduleRepository.deleteSchedule]
     */
    override suspend fun deleteSchedule(
        token: String,
        body: DeleteScheduleRequest
    ): NetworkResult<Unit> {
        return handleResult { dataSource.deleteSchedule(token, body) }
    }

    /**
     * 일정 수락
     * implements [ScheduleRepository.acceptSchedule]
     */
    override suspend fun acceptSchedule(
        token: String,
        body: AcceptScheduleRequest
    ): NetworkResult<Boolean> {
        return handleResult { dataSource.acceptSchedule(token, body) }
    }

    /**
     * 일정 거절 또는 나가기
     * implements [ScheduleRepository.refuseOrQuitSchedule]
     */
    override suspend fun refuseOrQuitSchedule(
        token: String,
        body: RefuseOrQuitScheduleRequest
    ): NetworkResult<Unit> {
        return handleResult { dataSource.refuseOrQuitSchedule(token, body) }
    }

    /**
     * 도착 여부
     * implements [ScheduleRepository.checkArrival]
     */
    override suspend fun checkArrival(
        token: String,
        body: CheckArrivalRequest
    ): NetworkResult<Boolean> {
        return handleResult { dataSource.checkArrival(token, body) }
    }

    /**
     * 일정 초대 목록
     * implements [ScheduleRepository.checkArrival]
     */
    override suspend fun getScheduleInvitation(
        token: String,
        memberId: String
    ): NetworkResult<GetScheduleInvitationResponse> {
        return handleResult { dataSource.getScheduleInvitation(token, memberId) }
    }
}