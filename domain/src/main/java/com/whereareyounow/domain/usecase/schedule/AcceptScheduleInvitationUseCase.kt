package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.request.schedule.AcceptScheduleInvitationRequest
import kotlinx.coroutines.flow.flow

class AcceptScheduleInvitationUseCase(
    private val repository: ScheduleRepository
) {
    operator fun invoke(
        data: AcceptScheduleInvitationRequest
    ) = flow {
        val response = repository.acceptScheduleRequest(data)
        emit(response)
    }
}