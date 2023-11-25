package com.whereareyou.domain.usecase.schedule

import com.whereareyou.domain.entity.apimessage.schedule.ModifyScheduleMemberRequest
import com.whereareyou.domain.repository.ScheduleRepository
import com.whereareyou.domain.util.NetworkResult

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