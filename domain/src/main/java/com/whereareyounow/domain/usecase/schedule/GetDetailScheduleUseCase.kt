package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.entity.apimessage.schedule.GetDetailScheduleResponse
import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.util.NetworkResult

class GetDetailScheduleUseCase(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(
        token: String,
        memberId: String,
        scheduleId: String
    ): NetworkResult<GetDetailScheduleResponse> {
        return repository.getDetailSchedule(token, memberId, scheduleId)
    }
}