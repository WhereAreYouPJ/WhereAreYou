package com.whereareyounow.ui.home.friends

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.FriendProvider
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendIdsListRequest
import com.whereareyounow.domain.entity.apimessage.friend.GetFriendListRequest
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.domain.usecase.friend.GetFriendIdsListUseCase
import com.whereareyounow.domain.usecase.friend.GetFriendListUseCase
import com.whereareyounow.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyounow.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val application: Application,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val getFriendIdsListUseCase: GetFriendIdsListUseCase,
    private val getFriendListUseCase: GetFriendListUseCase,
    ) : AndroidViewModel(application) {

    val friendsList = mutableStateListOf<Friend>()

    fun getFriendIdsList() {
        viewModelScope.launch(Dispatchers.Default) {
            // 친구 memberId 리스트를 가져온다.
            val accessToken = getAccessTokenUseCase().first()
            val myMemberId = getMemberIdUseCase().first()
            val getFriendIdsRequest = GetFriendIdsListRequest(myMemberId)

            when (val networkResponse = getFriendIdsListUseCase(accessToken, getFriendIdsRequest)) {
                is NetworkResult.Success -> {
                    networkResponse.data?.let { data ->
                        getFriendsList(data.friendsIdList)
                        Log.e("getFriendIds Success", "${data.friendsIdList}")
                    }
                }
                is NetworkResult.Error -> { Log.e("error", "${networkResponse.code}, ${networkResponse.errorData}") }
                is NetworkResult.Exception -> { Log.e("exception", "${networkResponse.e}") }
            }

            // 가져온 친구 memberId 리스트로 친구 상세정보 리스트를 가져온다.
        }
    }

    fun getFriendsList(friendIds: List<String>) {
        // 친구 memberId 리스트로 친구 상세정보 리스트를 가져온다.
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val getFriendListRequest = GetFriendListRequest(friendIds)

            when (val networkResponse = getFriendListUseCase(accessToken, getFriendListRequest)) {
                is NetworkResult.Success -> {
                    networkResponse.data?.let { data ->
                        val sortedList = data.friendsList.sortedBy { it.name }
                        for (i in sortedList.indices) {
                            sortedList[i].number = i
                        }
                        friendsList.clear()
                        friendsList.addAll(sortedList)
                        FriendProvider.friendsList.clear()
                        FriendProvider.friendsList.addAll(sortedList)
                        Log.e("getFriendIds Success", "${sortedList}")
                    }
                }
                is NetworkResult.Error -> { Log.e("error", "${networkResponse.code}, ${networkResponse.errorData}") }
                is NetworkResult.Exception -> { Log.e("exception", "${networkResponse.e}") }
            }
        }
    }

    init {
        getFriendIdsList()
    }
}