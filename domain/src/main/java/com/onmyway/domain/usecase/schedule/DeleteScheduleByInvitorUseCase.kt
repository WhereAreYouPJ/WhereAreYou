package com.onmyway.domain.usecase.schedule

import com.onmyway.domain.repository.ScheduleRepository
import com.onmyway.domain.request.schedule.DeleteScheduleRequest
import kotlinx.coroutines.flow.flow

class DeleteScheduleByInvitorUseCase(
    private val repository: ScheduleRepository
) {
    operator fun invoke(
        data: DeleteScheduleRequest
    ) = flow {
        val response = repository.deleteScheduleByInvitor(data)
        emit(response)
    }
}