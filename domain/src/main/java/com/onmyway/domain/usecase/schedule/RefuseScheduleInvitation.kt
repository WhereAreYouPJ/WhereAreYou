package com.onmyway.domain.usecase.schedule

import com.onmyway.domain.repository.ScheduleRepository
import com.onmyway.domain.request.schedule.RefuseScheduleInvitationRequest
import kotlinx.coroutines.flow.flow

class RefuseScheduleInvitation(
    private val repository: ScheduleRepository
) {
    operator fun invoke(
        data: RefuseScheduleInvitationRequest
    ) = flow {
        val response = repository.refuseScheduleInvitation(data)
        emit(response)
    }
}
