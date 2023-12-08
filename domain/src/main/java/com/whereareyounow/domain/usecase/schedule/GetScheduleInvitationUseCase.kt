package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.entity.apimessage.schedule.GetScheduleInvitationResponse
import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.util.NetworkResult

class GetScheduleInvitationUseCase(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(
        token: String,
        memberId: String
    ): NetworkResult<GetScheduleInvitationResponse> {
        return repository.getScheduleInvitation(token, memberId)
    }
}