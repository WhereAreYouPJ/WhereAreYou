package com.whereareyou.domain.usecase.schedule

import com.whereareyou.domain.entity.schedule.DetailSchedule
import com.whereareyou.domain.repository.ScheduleRepository
import com.whereareyou.domain.util.NetworkResult

class GetDetailScheduleUseCase(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(
        token: String,
        memberId: String,
        scheduleId: String
    ): NetworkResult<DetailSchedule> {
        return repository.getDetailSchedule(token, memberId, scheduleId)
    }
}