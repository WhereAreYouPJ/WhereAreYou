package com.onmyway.domain.usecase.schedule

import com.onmyway.domain.repository.ScheduleRepository
import com.onmyway.domain.request.schedule.CreateNewScheduleRequest
import kotlinx.coroutines.flow.flow

class CreateNewScheduleUseCase(
    private val repository: ScheduleRepository
) {
    operator fun invoke(
        data: CreateNewScheduleRequest
    ) = flow {
        val response = repository.createNewSchedule(data)
        emit(response)
    }
}