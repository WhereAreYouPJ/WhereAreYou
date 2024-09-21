package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.request.schedule.RefuseScheduleInvitationRequest
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
