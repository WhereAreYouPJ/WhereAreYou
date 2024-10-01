package com.whereareyounow.ui.main.friend

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import com.whereareyounow.domain.entity.schedule.Friend
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val application: Application,
//    private val getAccessTokenUseCase: GetAccessTokenUseCase,
//    private val getMemberIdUseCase: GetMemberIdUseCase,
//    private val getFriendIdsListUseCase: GetFriendIdsListUseCase,
//    private val getFriendListUseCase: GetFriendListUseCase,
    ) : AndroidViewModel(application) {

    val friendsList = mutableStateListOf<Friend>(
    )
    // 이게 찐 위는 짭테스트
//    val friendsList = mutableStateListOf<Friend>()

    fun getFriendIdsList() {
//        viewModelScope.launch(Dispatchers.Default) {
//            // 친구 memberId 리스트를 가져온다.
//            val accessToken = getAccessTokenUseCase().first()
//            val myMemberId = getMemberIdUseCase().first()
//            val request =
//                com.whereareyounow.domain.request.friend.GetFriendIdsListRequest(myMemberId)
//            val response = getFriendIdsListUseCase(accessToken, request)
//            LogUtil.printNetworkLog(request, response, "친구 memberId 리스트 가져오기")
//            when (response) {
//                is NetworkResult.Success -> {
//                    response.data?.let { data ->
//                        getFriendsList(data.friendsIdList)
//                    }
//                }
//                is NetworkResult.Error -> {  }
//                is NetworkResult.Exception -> {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//
//            // 가져온 친구 memberId 리스트로 친구 상세정보 리스트를 가져온다.
//        }
    }

    fun getFriendsList(friendIds: List<String>) {
//        // 친구 memberId 리스트로 친구 상세정보 리스트를 가져온다.
//        viewModelScope.launch(Dispatchers.Default) {
//            val accessToken = getAccessTokenUseCase().first()
//            val request =
//                com.whereareyounow.domain.request.friend.GetFriendListRequest(friendIds)
//            val response = getFriendListUseCase(accessToken, request)
//            LogUtil.printNetworkLog(request, response, "친구 리스트 가져오기")
//            when (response) {
//                is NetworkResult.Success -> {
//                    response.data?.let { data ->
//                        val sortedList = data.friendsList.sortedBy { it.name }
//                        for (i in sortedList.indices) {
//                            sortedList[i].number = i
//                        }
//                        friendsList.clear()
//                        friendsList.addAll(sortedList)
//                        FriendProvider.friendsList.clear()
//                        FriendProvider.friendsList.addAll(sortedList)
//                    }
//                }
//                is NetworkResult.Error -> {  }
//                is NetworkResult.Exception -> {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }
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