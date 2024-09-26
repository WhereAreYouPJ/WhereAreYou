package com.onmyway.domain.usecase.schedule

import com.onmyway.domain.repository.ScheduleRepository
import com.onmyway.domain.request.schedule.GetDetailScheduleRequest
import kotlinx.coroutines.flow.flow

class GetDetailScheduleUseCase(
    private val repository: ScheduleRepository
) {
    operator fun invoke(
        data: GetDetailScheduleRequest
    ) = flow {
        val response = repository.getDetailSchedule(data)
        emit(response)
    }
}