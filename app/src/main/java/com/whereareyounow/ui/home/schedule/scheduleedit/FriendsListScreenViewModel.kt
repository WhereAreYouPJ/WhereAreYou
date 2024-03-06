package com.whereareyounow.ui.home.schedule.scheduleedit

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.whereareyounow.data.FriendProvider
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.util.SoundSearcherUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FriendsListScreenViewModel @Inject constructor() : ViewModel() {
    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    // 전체 친구 리스트
    // Pair의 first는 친구 정보, second는 선택 여부를 나타낸다.
    private val originalFriendsList = mutableStateListOf<Pair<Friend, Boolean>>().apply {
        addAll(FriendProvider.friendsList.map { Pair(it, false) })
    }

    // 검색에 의해 필터링되어 하단에 보여지는 친구 리스트
    private val _searchedFriendsList = mutableStateListOf<Pair<Friend, Boolean>>().apply { addAll(originalFriendsList) }
    val searchedFriendsList: List<Pair<Friend, Boolean>> = _searchedFriendsList

    // 선택되어 상단에 보여지는 친구 리스트
    private val _selectedFriendsList = mutableStateListOf<Friend>()
    val selectedFriendsList: List<Friend> = _selectedFriendsList

    // 검색창에 입력된 텍스트에 따라 친구 리스트를 필터링한다.
    fun updateInputText(text: String) {
        _inputText.update { text }
        _searchedFriendsList.clear()
        _searchedFriendsList.addAll(originalFriendsList.filter { SoundSearcherUtil.matchString(it.first.name, text) })
    }

    fun selectFriend(friend: Pair<Friend, Boolean>) {
        // 오리지널 친구 목록의 선택 여부를 변경한다.
        for (i in originalFriendsList.indices) {
            if (originalFriendsList[i].first.memberId == friend.first.memberId) {
                originalFriendsList[i] = friend.first to !friend.second
                break
            }
        }

        // 검색된 친구 목록을 업데이트한다.
        for (i in _searchedFriendsList.indices) {
            if (_searchedFriendsList[i].first.memberId == friend.first.memberId) {
                _searchedFriendsList[i] = friend.first to !friend.second
                break
            }
        }

        // 선택된 친구 목록을 업데이트한다.
        if (friend.second) {
            // false가 되므로 목록에서 제거해야 한다.
            _selectedFriendsList.removeIf { it.memberId == friend.first.memberId }
        } else {
            // true가 되므로 목록에 추가해야 한다.
            _selectedFriendsList.add(friend.first)
            _selectedFriendsList.sortBy { it.number }
        }
    }

    // 초기에 선택된 친구 목록을 유지
    fun initialize(idsList: List<String>) {
        originalFriendsList.clear()
        originalFriendsList.addAll(FriendProvider.friendsList.map { Pair(it, false) })
        _searchedFriendsList.clear()
        _searchedFriendsList.addAll(originalFriendsList)
        _selectedFriendsList.clear()
        FriendProvider.friendsList.forEach { friend ->
            if (friend.memberId in idsList) {
                selectFriend(friend to false)
            }
        }
    }
}