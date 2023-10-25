package com.whereareyou.ui.home.main.newschedule

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyou.data.Friend
import com.whereareyou.domain.repository.ScheduleRepository
import com.whereareyou.repository.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewScheduleViewModel @Inject constructor(
    sharedPreferencesRepository: SharedPreferencesRepository,
    remoteRepository: ScheduleRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _screenState = MutableStateFlow(ScreenState.NewSchedule)
    val screenState: StateFlow<ScreenState> = _screenState

    fun updateScreenState(state: ScreenState) {
        _screenState.update {
            state
        }
    }

    private val _friendsList = MutableStateFlow(emptyList<Friend>())
    val friendsList: StateFlow<List<Friend>> = _friendsList


    private val _startDate = MutableStateFlow("시작 날짜 선택")
    val startDate: StateFlow<String> = _startDate
    private val _endDate = MutableStateFlow("종료 날짜 선택")
    val endDate: StateFlow<String> = _endDate

    private val _startTime = MutableStateFlow("시작 시간 선택")
    val startTime: StateFlow<String> = _startTime
    private val _endTime = MutableStateFlow("종료 시간 선택")
    val endTime: StateFlow<String> = _endTime

    private val _destinationName = MutableStateFlow("")
    val destinationName: StateFlow<String> = _destinationName
    private val _destinationAddress = MutableStateFlow("")
    val destinationAddress: StateFlow<String> = _destinationAddress

    fun updateStartDate(date: String) {
        _startDate.update {
            date
        }
    }

    fun updateEndDate(date: String) {
        _endDate.update {
            date
        }
    }

    fun updateStartTime(time: String) {
        _startTime.update {
            time
        }
    }

    fun updateEndTime(time: String) {
        _endTime.update {
            time
        }
    }

    fun updateFriendsList(friends: List<Friend>) {
        _friendsList.update {
            friends
        }
    }

    fun updateDestinationInformation(name: String, address: String) {
        _destinationName.update { name }
        _destinationAddress.update { address }
    }

    fun addSchedule() {
        viewModelScope.launch {

        }
    }

    enum class ScreenState {
        NewSchedule, AddFriends, SearchLocation, Map
    }
}