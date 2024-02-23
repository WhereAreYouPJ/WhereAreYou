package com.whereareyounow.ui.home.schedule.scheduleedit

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.scheduleedit.ScheduleEditScreenUIState
import com.whereareyounow.domain.entity.apimessage.schedule.AddNewScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.ModifyScheduleDetailsRequest
import com.whereareyounow.domain.entity.apimessage.schedule.ModifyScheduleMemberRequest
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.domain.usecase.schedule.AddNewScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.ModifyScheduleDetailsUseCase
import com.whereareyounow.domain.usecase.schedule.ModifyScheduleMemberUseCase
import com.whereareyounow.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ScheduleEditViewModel @Inject constructor(
    private val application: Application,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val addNewScheduleUseCase: AddNewScheduleUseCase,
    private val modifyScheduleDetailsUseCase: ModifyScheduleDetailsUseCase,
    private val modifyScheduleMemberUseCase: ModifyScheduleMemberUseCase,
) : AndroidViewModel(application) {

    private val _screenState = MutableStateFlow(ScreenState.EditSchedule)
    val screenState: StateFlow<ScreenState> = _screenState

    fun updateScreenState(state: ScreenState) {
        _screenState.update { state }
    }

    private var scheduleId = ""
    private val _scheduleEditScreenUIState = MutableStateFlow(ScheduleEditScreenUIState())
    val scheduleEditScreenUIState = _scheduleEditScreenUIState.asStateFlow()
//    private val _selectedFriendsList = mutableStateListOf<Friend>()
//    val selectedFriendsList: List<Friend> = _selectedFriendsList
//    private val _scheduleName = MutableStateFlow("")
//    val scheduleName: StateFlow<String> = _scheduleName
//    private val _scheduleYear = MutableStateFlow("0")
//    val scheduleYear: StateFlow<String> = _scheduleYear
//    private val _scheduleMonth = MutableStateFlow("0")
//    val scheduleMonth: StateFlow<String> = _scheduleMonth
//    private val _scheduleDate = MutableStateFlow("0")
//    val scheduleDate: StateFlow<String> = _scheduleDate
//    private val _scheduleHour = MutableStateFlow("0")
//    val scheduleHour: StateFlow<String> = _scheduleHour
//    private val _scheduleMinute = MutableStateFlow("0")
//    val scheduleMinute: StateFlow<String> = _scheduleMinute
//    private val _destinationName = MutableStateFlow("")
//    val destinationName: StateFlow<String> = _destinationName
//    private val _destinationAddress = MutableStateFlow("")
//    val destinationAddress: StateFlow<String> = _destinationAddress
//    private val _destinationLatitude = MutableStateFlow(0.0)
//    val destinationLatitude: StateFlow<Double> = _destinationLatitude
//    private val _destinationLongitude = MutableStateFlow(0.0)
//    val destinationLongitude: StateFlow<Double> = _destinationLongitude
//    private val _memo = MutableStateFlow("")
//    val memo: StateFlow<String> = _memo

    fun updateScheduleId(id: String) {
        scheduleId = id
    }

    fun updateSelectedFriendsList(friends: List<Friend>) {
        _scheduleEditScreenUIState.update {
            it.copy(selectedFriendsList = friends)
        }
//        _selectedFriendsList.clear()
//        _selectedFriendsList.addAll(friends)
    }

    fun updateScheduleName(name: String) {
        _scheduleEditScreenUIState.update {
            it.copy(scheduleName = name)
        }
//        _scheduleName.update { name }
    }

    fun updateScheduleDate(date: String) {
        _scheduleYear.update { date.split("-")[0] }
        _scheduleMonth.update { date.split("-")[1] }
        _scheduleDate.update { date.split("-")[2] }
    }

    fun updateScheduleTime(time: String) {
        _scheduleHour.update { time.split(":")[0] }
        _scheduleMinute.update { time.split(":")[1] }
    }

    fun updateDestinationInformation(name: String, address: String, lat: Double, lng: Double) {
        _destinationName.update { name }
        _destinationAddress.update { address }
        _destinationLatitude.update { lat }
        _destinationLongitude.update { lng }
    }

    fun clearDestinationInformation() {
        _destinationName.update { "" }
        _destinationAddress.update { "" }
        _destinationLatitude.update { 0.0 }
        _destinationLongitude.update { 0.0 }
    }

    fun updateMemo(memo: String) {
        _memo.update { memo }
    }

    fun addNewSchedule(moveToBackScreen: () -> Unit) {
        if (_scheduleName.value == "") {
            Toast.makeText(application, "제목을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if (_scheduleDate.value == "약속 날짜 선택") {
            Toast.makeText(application, "약속 날짜를 선택해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if (_scheduleHour.value == "" || _scheduleMinute.value == "") {
            Toast.makeText(application, "약속 시간을 선택해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if (_destinationName.value == "") {
            Toast.makeText(application, "목적지를 선택해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val request = AddNewScheduleRequest(
                memberId = memberId,
                scheduleTime = "${_scheduleYear.value}-${_scheduleMonth.value}-${_scheduleDate.value}T${_scheduleHour.value}:${_scheduleMinute.value}:00",
                scheduleName = _scheduleName.value,
                destinationName = _destinationName.value,
                destinationAddress = _destinationAddress.value,
                memo = _memo.value,
                destinationLatitude = _destinationLatitude.value,
                destinationLongitude = _destinationLongitude.value,
                selectedMemberIdsList = _selectedFriendsList.map { friend -> friend.memberId }
            )
            val response = addNewScheduleUseCase(accessToken, request)
            LogUtil.printNetworkLog(response, "addNewScheduleUseCase")
            when (response) {
                is NetworkResult.Success -> {
                    withContext(Dispatchers.Main) {
                        moveToBackScreen()
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

    fun modifySchedule(moveToBackScreen: () -> Unit) {
        if (_scheduleName.value == "") {
            Toast.makeText(application, "일정명을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if (_scheduleDate.value == "약속 날짜 선택") {
            Toast.makeText(application, "약속 날짜를 선택해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if (_scheduleHour.value == "" || _scheduleMinute.value == "") {
            Toast.makeText(application, "약속 시간을 선택해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if (_destinationName.value == "") {
            Toast.makeText(application, "목적지를 선택해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch(Dispatchers.Default) {
            modifyScheduleDetails()
            modifyScheduleMember(moveToBackScreen)
        }
    }

    private suspend fun modifyScheduleDetails() {
        val accessToken = getAccessTokenUseCase().first()
        val memberId = getMemberIdUseCase().first()
        val request = ModifyScheduleDetailsRequest(
            creatorId = memberId,
            scheduleId = scheduleId,
            scheduleTime = "${_scheduleYear.value}-${_scheduleMonth.value}-${_scheduleDate.value}T${_scheduleHour.value}:${_scheduleMinute.value}:00",
            scheduleName = _scheduleName.value,
            destinationName = _destinationName.value,
            destinationAddress = _destinationAddress.value,
            memo = _memo.value,
            destinationLatitude = _destinationLatitude.value,
            destinationLongitude = _destinationLongitude.value
        )
        val response = modifyScheduleDetailsUseCase(accessToken, request)
        LogUtil.printNetworkLog(response, "일정 내용 수정")
        when (response) {
            is NetworkResult.Success -> {

            }
            is NetworkResult.Error -> {

            }
            is NetworkResult.Exception -> {
                withContext(Dispatchers.Main) {
                    Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun modifyScheduleMember(moveToBackScreen: () -> Unit) {
        val accessToken = getAccessTokenUseCase().first()
        val memberId = getMemberIdUseCase().first()
        val request = ModifyScheduleMemberRequest(
            creatorId = memberId,
            scheduleId = scheduleId,
            friendIds = _selectedFriendsList.map { it.memberId }
        )
        val response = modifyScheduleMemberUseCase(accessToken, request)
        LogUtil.printNetworkLog(response, "일정 멤버 수정")
        when (response) {
            is NetworkResult.Success -> {
                withContext(Dispatchers.Main) {
                    moveToBackScreen()
                }
            }
            is NetworkResult.Error -> {

            }
            is NetworkResult.Exception -> {
                withContext(Dispatchers.Main) {
                    Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    enum class ScreenState {
        EditSchedule, AddFriends, SearchLocation, Map
    }
}