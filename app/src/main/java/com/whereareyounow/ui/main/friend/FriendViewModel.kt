package com.whereareyounow.ui.main.friend

import android.app.Application
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val application: Application,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val getFriendIdsListUseCase: GetFriendIdsListUseCase,
    private val getFriendListUseCase: GetFriendListUseCase,
    ) : AndroidViewModel(application) {

    val friendsList = mutableStateListOf<Friend>(
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁1",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁2",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁3",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁4",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁5",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁6",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = false
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁7",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁8",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁9",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁10",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁11",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁12",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁13",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁14",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁15",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = false
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁16",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁17",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = false
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁18",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = false
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁19",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁20",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = false
        ),Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁21",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        ),Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁22",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁23",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        ),
        Friend(
            number = 0,
            memberId = "유민혁",
            name = "유민혁",
            userId = "유민혁24",
            profileImgUrl = "https://m.segye.com/content/image/2021/11/16/20211116509557.jpg",
            isPinned = true
        )




    )
    // 이게 찐 위는 짭테스트
//    val friendsList = mutableStateListOf<Friend>()

    fun getFriendIdsList() {
        viewModelScope.launch(Dispatchers.Default) {
            // 친구 memberId 리스트를 가져온다.
            val accessToken = getAccessTokenUseCase().first()
            val myMemberId = getMemberIdUseCase().first()
            val request = GetFriendIdsListRequest(myMemberId)
            val response = getFriendIdsListUseCase(accessToken, request)
            LogUtil.printNetworkLog(request, response, "친구 memberId 리스트 가져오기")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        getFriendsList(data.friendsIdList)
                    }
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            // 가져온 친구 memberId 리스트로 친구 상세정보 리스트를 가져온다.
        }
    }

    fun getFriendsList(friendIds: List<String>) {
        // 친구 memberId 리스트로 친구 상세정보 리스트를 가져온다.
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val request = GetFriendListRequest(friendIds)
            val response = getFriendListUseCase(accessToken, request)
            LogUtil.printNetworkLog(request, response, "친구 리스트 가져오기")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        val sortedList = data.friendsList.sortedBy { it.name }
                        for (i in sortedList.indices) {
                            sortedList[i].number = i
                        }
                        friendsList.clear()
                        friendsList.addAll(sortedList)
                        FriendProvider.friendsList.clear()
                        FriendProvider.friendsList.addAll(sortedList)
                    }
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    // 올믹
    data class MyInfo(
        val image : String?,
        val name : String
    )
    private val _myInfo = MutableStateFlow<MyInfo>( MyInfo("https://m.segye.com/content/image/2021/11/16/20211116509557.jpg" , "유민혁"))
    val myInfo : MutableStateFlow<MyInfo> = _myInfo
    // TODO 준성님개인정보어디서가져오는건지
//    private fun getMyInfo() {
//        _myInfo.value =
//    }


    init {
        getFriendIdsList()
    }
}