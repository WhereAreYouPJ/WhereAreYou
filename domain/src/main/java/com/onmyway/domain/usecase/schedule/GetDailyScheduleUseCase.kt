package com.onmyway.domain.usecase.schedule

import com.onmyway.domain.repository.ScheduleRepository
import com.onmyway.domain.request.schedule.GetDailyScheduleRequest
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