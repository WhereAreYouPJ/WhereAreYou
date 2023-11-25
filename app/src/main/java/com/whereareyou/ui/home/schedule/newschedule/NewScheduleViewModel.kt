package com.whereareyou.ui.home.schedule.newschedule

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyou.data.Friend
import com.whereareyou.domain.entity.apimessage.schedule.AddNewScheduleRequest
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
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val addNewScheduleUseCase: AddNewScheduleUseCase,
    application: Application
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

    fun updateFriendsList(friends: List<Friend>) {
        _friendsList.update { friends }
    }

    fun updateDestinationInformation(name: String, address: String, lat: Double, lng: Double) {
        _destinationName.update { name }
        _destinationAddress.update { address }
        _destinationLatitude.update { lat }
        _destinationLongitude.update { lng }
    }

    fun addNewSchedule() {
        viewModelScope.launch {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val body = AddNewScheduleRequest(memberId, startDate.value + "T" + startTime.value + ":00", endDate.value + "T" + endTime.value + ":00", _title.value, _destinationName.value, _destinationAddress.value, _destinationLatitude.value, _destinationLongitude.value, listOf("1d92d4e1-cd85-470b-aec2-712914f9f790"))
            val addNewScheduleResult = addNewScheduleUseCase(accessToken, body)
            when (addNewScheduleResult) {
                is NetworkResult.Success -> {
                    Log.e("success", "${addNewScheduleResult.data}")
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