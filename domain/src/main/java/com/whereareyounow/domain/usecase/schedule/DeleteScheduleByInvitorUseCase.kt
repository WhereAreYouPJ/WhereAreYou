package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.request.schedule.DeleteScheduleRequest
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