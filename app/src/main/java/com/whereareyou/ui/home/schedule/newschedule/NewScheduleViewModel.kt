package com.whereareyou.ui.home.schedule.newschedule

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyou.domain.entity.apimessage.schedule.AddNewScheduleRequest
import com.whereareyou.domain.entity.schedule.Friend
import com.whereareyou.domain.usecase.schedule.AddNewScheduleUseCase
import com.whereareyou.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyou.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyou.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewScheduleViewModel @Inject constructor(
    private val application: Application,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val addNewScheduleUseCase: AddNewScheduleUseCase,
) : AndroidViewModel(application) {

    private val _screenState = MutableStateFlow(ScreenState.NewSchedule)
    val screenState: StateFlow<ScreenState> = _screenState

    fun updateScreenState(state: ScreenState) {
        _screenState.update { state }
    }

    private val _friendsList = MutableStateFlow(emptyList<Friend>())
    val friendsList: StateFlow<List<Friend>> = _friendsList
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

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
    private val _destinationLatitude = MutableStateFlow(0.0)
    val destinationLatitude: StateFlow<Double> = _destinationLatitude
    private val _destinationLongitude = MutableStateFlow(0.0)
    val destinationLongitude: StateFlow<Double> = _destinationLongitude

    private val _memo = MutableStateFlow("")
    val memo: StateFlow<String> = _memo

    fun updateStartDate(date: String) {
        _startDate.update { date }
    }

    fun updateTitle(title: String) {
        _title.update { title }
    }

    fun clearTitle() {
        _title.update { "" }
    }

    fun updateEndDate(date: String) {
        _endDate.update { date }
    }

    fun updateStartTime(time: String) {
        _startTime.update { time }
    }

    fun updateEndTime(time: String) {
        _endTime.update { time }
    }

    fun updateMemo(memo: String) {
        _memo.update { memo }
    }

    fun updateFriendsList(friends: List<Friend>) {
        _friendsList.update { friends }
    }

    fun updateDestinationInformation(name: String, address: String, lat: Double, lng: Double) {
        _destinationName.update { name }
        _destinationAddress.update { address }
        _destinationLatitude.update { lat }
        _destinationLongitude.update { lng }
    }

    fun addNewSchedule(
        moveToCalendarScreen: () -> Unit,
    ) {
        if (_startDate.value == "시작 날짜 선택") {
            Toast.makeText(application, "시작 날짜를 선택해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if (_endDate.value == "종료 날짜 선택") {
            Toast.makeText(application, "종료 날짜를 선택해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if (_startTime.value == "시작 시간 선택") {
            Toast.makeText(application, "시작 시간을 선택해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if (_endTime.value == "종료 시간 선택") {
            Toast.makeText(application, "종료 시간을 선택해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if (_destinationName.value == "") {
            Toast.makeText(application, "목적지를 선택해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val body = AddNewScheduleRequest(
                memberId = memberId,
                start = startDate.value + "T" + startTime.value + ":00",
                end = endDate.value + "T" + endTime.value + ":00",
                title = _title.value,
                place = _destinationName.value,
                memo = _memo.value,
                destinationLatitude = _destinationLatitude.value,
                destinationLongitude = _destinationLongitude.value,
                memberIdList = _friendsList.value.map { friend -> friend.memberId },)
            val addNewScheduleResult = addNewScheduleUseCase(accessToken, body)
            when (addNewScheduleResult) {
                is NetworkResult.Success -> {
                    Log.e("success", "${addNewScheduleResult.data}")
                    moveToCalendarScreen()
                }
                is NetworkResult.Error -> { Log.e("error", "${addNewScheduleResult.code}, ${addNewScheduleResult.errorData}") }
                is NetworkResult.Exception -> { Log.e("exception", "exception") }
            }
        }
    }

    enum class ScreenState {
        NewSchedule, AddFriends, SearchLocation, Map
    }
}