package com.onmyway.domain.usecase.member

import com.onmyway.domain.repository.MemberRepository
import com.onmyway.domain.request.member.UpdateProfileImageRequest
import kotlinx.coroutines.flow.flow

class UpdateProfileImageUseCase(
    private val repository: MemberRepository
) {
    operator fun invoke(
        data: UpdateProfileImageRequest
    ) = flow {
        val response = repository.updateProfileImage(data)
        emit(response)
    }
}