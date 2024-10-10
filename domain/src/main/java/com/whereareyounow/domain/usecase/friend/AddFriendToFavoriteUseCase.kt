package com.whereareyounow.domain.usecase.friend

import com.whereareyounow.domain.repository.FriendRepository
import com.whereareyounow.domain.request.friend.AddFriendToFavoriteRequest
import kotlinx.coroutines.flow.flow

class AddFriendToFavoriteUseCase(
    private val repository: FriendRepository
) {
    operator fun invoke(
        data: AddFriendToFavoriteRequest
    ) = flow {
        val response = repository.addFriendToFavorite(data)
        emit(response)
    }
}