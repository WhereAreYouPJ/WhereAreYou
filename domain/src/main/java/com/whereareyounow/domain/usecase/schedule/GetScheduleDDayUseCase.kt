package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.request.schedule.GetScheduleDDayRequest
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