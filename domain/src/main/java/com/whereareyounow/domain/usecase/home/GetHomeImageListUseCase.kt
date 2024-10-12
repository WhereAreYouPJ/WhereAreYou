package com.whereareyounow.domain.usecase.home

import com.whereareyounow.domain.repository.HomeRepository
import com.whereareyounow.domain.request.home.GetHomeImageListRequest
import kotlinx.coroutines.flow.flow

class GetHomeImageListUseCase(
    private val repository: HomeRepository
) {
    operator fun invoke(
        data: GetHomeImageListRequest
    ) = flow {
        val response = repository.getHomeImageList(data)
        emit(response)
    }
}