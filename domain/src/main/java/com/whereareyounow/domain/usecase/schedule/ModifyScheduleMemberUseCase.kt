package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.entity.apimessage.schedule.ModifyScheduleMemberRequest
import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.util.NetworkResult

class ModifyScheduleMemberUseCase(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(
        token: String,
        body: ModifyScheduleMemberRequest
    ): NetworkResult<Unit> {
        return repository.modifyScheduleMember(token, body)
    }
}