package com.onmyway.domain.usecase.schedule

import com.onmyway.domain.repository.ScheduleRepository
import com.onmyway.domain.request.schedule.ModifyScheduleInfoRequest
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