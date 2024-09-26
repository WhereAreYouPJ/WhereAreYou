package com.onmyway.domain.usecase.schedule

import com.onmyway.domain.repository.ScheduleRepository
import com.onmyway.domain.request.schedule.GetScheduleDDayRequest
import kotlinx.coroutines.flow.flow

class GetScheduleDDayUseCase(
    private val repository: ScheduleRepository
) {
    operator fun invoke(
        data: GetScheduleDDayRequest
    ) = flow {
        val response = repository.getScheduleDDay(data)
        emit(response)
    }
}