package com.whereareyou.ui.home.schedule.newschedule

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.whereareyou.data.Friend
import com.whereareyou.data.FriendProvider
import com.whereareyou.util.SoundSearcherUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class FriendsListScreenViewModel @Inject constructor(
    application: Application,
) : AndroidViewModel(application) {
    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText

    private val originalFriendsList: MutableList<Pair<Friend, Boolean>> = FriendProvider.friendsList.map {
        Pair(it, false)
    }.toMutableList()

    private val _friendsList = MutableStateFlow<List<Pair<Friend, Boolean>>>(originalFriendsList)
    val friendsListCopy: StateFlow<List<Pair<Friend, Boolean>>> = _friendsList

    private val _selectedFriendsList = MutableStateFlow<List<Friend>>(emptyList())
    val selectedFriendsList: StateFlow<List<Friend>> = _selectedFriendsList

    // 검색창에 입력된 텍스트에 따라 친구 리스트를 필터링한다
    fun updateInputText(text: String) {
        _inputText.update {
            text
        }
        _friendsList.update {
            originalFriendsList.filter {
                SoundSearcherUtil.matchString(it.first.name, text)
            }
        }
    }

    fun selectFriend(friend: Pair<Friend, Boolean>) {
        // 오리지널 친구 목록의 선택 여부를 변경한다
        for (i in originalFriendsList.indices) {
            if (originalFriendsList[i].first.number == friend.first.number) {
                originalFriendsList[i] = friend.first to !friend.second
                break
            }
        }
        Log.e("originalFriendsList", originalFriendsList.toString())
        // 보여지는 친구 목록의 선택 여부를 변경한다
        _friendsList.update {
            originalFriendsList.filter {
                SoundSearcherUtil.matchString(it.first.name, _inputText.value)
            }
        }

        // 선택된 친구 목록을 업데이트한다
        if (friend.second) {
            Log.e("remove", "remove ${friend.first.name}")
            // false가 되므로 목록에서 제거해야 한다
            for (i in 0 until selectedFriendsList.value.size) {
                if (friend.first.number == selectedFriendsList.value[i].number) {
                    _selectedFriendsList.update {
                        _selectedFriendsList.value.toMutableList().apply {
                            removeAt(i)
                        }
                    }
                    break
                }
            }
        } else {
            // true가 되므로 목록에 추가해야 한다

            Log.e("add", "add ${friend.first.name}")
            if (_selectedFriendsList.value.isEmpty()) {
                // 만약 선택된 친구 목록이 비어있으면 무조건 추가한다
                _selectedFriendsList.update {
                    listOf(friend.first)
                }
            } else {
                for (i in 0 until selectedFriendsList.value.size) {
                    if (friend.first.number == selectedFriendsList.value[i].number) {
                        // 이미 선택된 친구가 존재하면 break
                        break
                    }
                    if (friend.first.number < selectedFriendsList.value[i].number) {
                        _selectedFriendsList.update {
                            _selectedFriendsList.value.toMutableList().apply {
                                add(i, friend.first)
                            }
                        }
                        break
                    } else if (i == selectedFriendsList.value.size - 1) {
                        _selectedFriendsList.update {
                            _selectedFriendsList.value.toMutableList().apply {
                                add(friend.first)
                            }
                        }
                        break
                    }
                }
            }
        }
    }


}