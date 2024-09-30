package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.request.schedule.GetDailyScheduleRequest
import kotlinx.coroutines.flow.flow

class GetDailyScheduleUseCase(
    private val repository: ScheduleRepository
) {
    operator fun invoke(
        data: GetDailyScheduleRequest
    ) = flow {
        val response = repository.getDailySchedule(data)
        emit(response)
    }
}