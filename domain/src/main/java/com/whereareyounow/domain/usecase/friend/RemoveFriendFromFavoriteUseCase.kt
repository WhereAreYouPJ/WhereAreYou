package com.whereareyounow.domain.usecase.friend

import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.request.friend.RemoveFriendFromFavoriteRequest
import kotlinx.coroutines.flow.flow

class RemoveFriendFromFavoriteUseCase(
    private val repository: FriendRepository
) {
    operator fun invoke(
        data: RemoveFriendFromFavoriteRequest
    ) = flow {
        val response = repository.removeFriendFromFavorite(data)
        emit(response)
    }
}