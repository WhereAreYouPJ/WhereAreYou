package com.whereareyounow.domain.usecase.member

import com.whereareyounow.domain.repository.MemberRepository
import com.whereareyounow.domain.request.member.UpdateProfileImageRequest
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