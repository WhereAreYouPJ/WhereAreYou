package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.request.schedule.GetInvitedScheduleRequest
import kotlinx.coroutines.flow.flow

class GetInvitedScheduleUseCase(
    private val repository: ScheduleRepository
) {
    operator fun invoke(
        data: GetInvitedScheduleRequest
    ) = flow {
        val response = repository.getInvitedSchedule(data)
        emit(response)
    }
}