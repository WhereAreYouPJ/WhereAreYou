package com.whereareyounow.domain.usecase.schedule

import com.whereareyounow.domain.repository.ScheduleRepository
import com.whereareyounow.domain.request.schedule.ModifyScheduleInfoRequest
import kotlinx.coroutines.flow.flow

class ModifyScheduleInfoUseCase(
    private val repository: ScheduleRepository
) {
    operator fun invoke(
        data: ModifyScheduleInfoRequest
    ) = flow {
        val response = repository.modifyScheduleInfo(data)
        emit(response)
    }
}