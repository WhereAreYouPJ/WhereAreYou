package com.onmyway.ui.main.schedule.scheduleedit

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.onmyway.data.FriendProvider
import com.onmyway.data.detailschedule.DetailScheduleScreenUIState
import com.onmyway.data.scheduleedit.ScheduleEditScreenSideEffect
import com.onmyway.data.scheduleedit.ScheduleEditScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleEditViewModel @Inject constructor(
    private val application: Application,
//    private val getAccessTokenUseCase: GetAccessTokenUseCase,
//    private val getMemberIdUseCase: GetMemberIdUseCase,
//    private val addNewScheduleUseCase: AddNewScheduleUseCase,
//    private val modifyScheduleDetailsUseCase: ModifyScheduleDetailsUseCase,
//    private val modifyScheduleMemberUseCase: ModifyScheduleMemberUseCase,
) : AndroidViewModel(application) {

    private var scheduleId = ""
    private val _scheduleEditScreenUIState = MutableStateFlow(ScheduleEditScreenUIState())
    var isInitialized = false
    val scheduleEditScreenUIState = _scheduleEditScreenUIState.asStateFlow()
    val scheduleEditScreenSideEffectFlow = MutableSharedFlow<ScheduleEditScreenSideEffect>()
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

    fun updateSelectedFriendsList(friendIds: List<String>) {
        _scheduleEditScreenUIState.update {
            it.copy(selectedFriendsList = FriendProvider.friendsList.filter { friend ->
                friend.memberId in friendIds })
        }
    }

    fun updateScheduleName(name: String) {
        _scheduleEditScreenUIState.update {
            it.copy(scheduleName = name)
        }
    }

    fun updateScheduleDate(year: Int, month: Int, date: Int) {
        _scheduleEditScreenUIState.update {
            it.copy(
                scheduleYear = year,
                scheduleMonth = month,
                scheduleDate = date
            )
        }
    }

    fun updateScheduleTime(hour: Int, minute: Int) {
        _scheduleEditScreenUIState.update {
            it.copy(
                scheduleHour = hour,
                scheduleMinute = minute
            )
        }
    }

    fun updateDestinationInformation(name: String, address: String, lat: Double, lng: Double) {
        _scheduleEditScreenUIState.update {
            it.copy(
                destinationName = name,
                destinationAddress = address,
                destinationLatitude = lat,
                destinationLongitude = lng
            )
        }
    }

    fun updateMemo(memo: String) {
        _scheduleEditScreenUIState.update {
            it.copy(memo = memo)
        }
    }

    fun updateDestinationLocation(lat: Double, lng: Double) {
        _scheduleEditScreenUIState.update {
            it.copy(
                destinationLatitude = lat,
                destinationLongitude = lng
            )
        }
    }

    fun addNewSchedule(moveToBackScreen: () -> Unit) {
//        viewModelScope.launch(Dispatchers.Default) {
//            if (_scheduleEditScreenUIState.value.scheduleName == "") {
//                scheduleEditScreenSideEffectFlow.emit(ScheduleEditScreenSideEffect.Toast("제목을 입력해주세요."))
//                return@launch
//            }
//            if (_scheduleEditScreenUIState.value.scheduleDate == 0) {
//                scheduleEditScreenSideEffectFlow.emit(ScheduleEditScreenSideEffect.Toast("약속 날짜를 선택해주세요."))
//                return@launch
//            }
//            if (_scheduleEditScreenUIState.value.destinationName == "") {
//                scheduleEditScreenSideEffectFlow.emit(ScheduleEditScreenSideEffect.Toast("목적지를 선택해주세요."))
//                return@launch
//            }
//            val accessToken = getAccessTokenUseCase().first()
//            val memberId = getMemberIdUseCase().first()
//            val request = com.whereareyounow.domain.request.schedule.AddNewScheduleRequest(
//                memberId = memberId,
//                scheduleTime = String.format(
//                    "%04d",
//                    _scheduleEditScreenUIState.value.scheduleYear
//                ) +
//                        "-${
//                            String.format(
//                                "%02d",
//                                _scheduleEditScreenUIState.value.scheduleMonth
//                            )
//                        }" +
//                        "-${String.format("%02d", _scheduleEditScreenUIState.value.scheduleDate)}" +
//                        "T${String.format("%02d", _scheduleEditScreenUIState.value.scheduleHour)}" +
//                        ":${
//                            String.format(
//                                "%02d",
//                                _scheduleEditScreenUIState.value.scheduleMinute
//                            )
//                        }:00",
//                scheduleName = _scheduleEditScreenUIState.value.scheduleName,
//                destinationName = _scheduleEditScreenUIState.value.destinationName,
//                destinationAddress = _scheduleEditScreenUIState.value.destinationAddress,
//                memo = _scheduleEditScreenUIState.value.memo,
//                destinationLatitude = _scheduleEditScreenUIState.value.destinationLatitude,
//                destinationLongitude = _scheduleEditScreenUIState.value.destinationLongitude,
//                selectedMemberIdsList = _scheduleEditScreenUIState.value.selectedFriendsList.map { friend -> friend.memberId }
//            )
//            val response = addNewScheduleUseCase(accessToken, request)
//            LogUtil.printNetworkLog(request, response, "addNewScheduleUseCase")
//            when (response) {
//                is NetworkResult.Success -> {
//                    withContext(Dispatchers.Main) {
//                        moveToBackScreen()
//                    }
//                }
//                is NetworkResult.Error -> {  }
//                is NetworkResult.Exception -> { scheduleEditScreenSideEffectFlow.emit(ScheduleEditScreenSideEffect.Toast("목적지를 선택해주세요.")) }
//            }
//        }
    }

    fun updateScheduleDetails(details: DetailScheduleScreenUIState) {
//        viewModelScope.launch(Dispatchers.Default) {
//            val myMemberId = getMemberIdUseCase().first()
//            _scheduleEditScreenUIState.update { state ->
//                state.copy(
//                    selectedFriendsList = details.memberInfosList.map {
//                        Friend(
//                            memberId = it.memberId,
//                            name = it.name,
//                            userId = it.userId,
//                            profileImgUrl = it.profileImage,
//                        )
//                    }.filter { it.memberId != myMemberId },
//                    scheduleName = details.scheduleName,
//                    scheduleYear = details.scheduleYear,
//                    scheduleMonth = details.scheduleMonth,
//                    scheduleDate = details.scheduleDate,
//                    scheduleHour = details.scheduleHour,
//                    scheduleMinute = details.scheduleMinute,
//                    destinationName = details.destinationName,
//                    destinationAddress = details.destinationRoadAddress,
//                    memo = details.memo
//                )
//            }
//            isInitialized = true
//        }
    }

    fun modifySchedule(moveToBackScreen: () -> Unit) {
        viewModelScope.launch(Dispatchers.Default) {
            if (_scheduleEditScreenUIState.value.scheduleName == "") {
                scheduleEditScreenSideEffectFlow.emit(ScheduleEditScreenSideEffect.Toast("일정명을 입력해주세요."))
                return@launch
            }
            if (_scheduleEditScreenUIState.value.scheduleDate == 0) {
                scheduleEditScreenSideEffectFlow.emit(ScheduleEditScreenSideEffect.Toast("약속 날짜를 선택해주세요."))
                Toast.makeText(application, "", Toast.LENGTH_SHORT).show()
                return@launch
            }
            if (_scheduleEditScreenUIState.value.destinationName == "") {
                scheduleEditScreenSideEffectFlow.emit(ScheduleEditScreenSideEffect.Toast("목적지를 선택해주세요."))
                return@launch
            }
            modifyScheduleDetails()
            modifyScheduleMember(moveToBackScreen)
        }
    }

    private suspend fun modifyScheduleDetails() {
//        val accessToken = getAccessTokenUseCase().first()
//        val memberId = getMemberIdUseCase().first()
//        val request = com.whereareyounow.domain.request.schedule.ModifyScheduleInfoRequest(
//            creatorId = memberId,
//            scheduleId = scheduleId,
//            scheduleTime = "${
//                String.format(
//                    "%04d",
//                    _scheduleEditScreenUIState.value.scheduleYear
//                )
//            }" +
//                    "-${String.format("%02d", _scheduleEditScreenUIState.value.scheduleMonth)}" +
//                    "-${String.format("%02d", _scheduleEditScreenUIState.value.scheduleDate)}" +
//                    "T${String.format("%02d", _scheduleEditScreenUIState.value.scheduleHour)}" +
//                    ":${String.format("%02d", _scheduleEditScreenUIState.value.scheduleMinute)}:00",
//            scheduleName = _scheduleEditScreenUIState.value.scheduleName,
//            destinationName = _scheduleEditScreenUIState.value.destinationName,
//            destinationAddress = _scheduleEditScreenUIState.value.destinationAddress,
//            memo = _scheduleEditScreenUIState.value.memo,
//            destinationLatitude = _scheduleEditScreenUIState.value.destinationLatitude,
//            destinationLongitude = _scheduleEditScreenUIState.value.destinationLongitude
//        )
//        val response = modifyScheduleDetailsUseCase(accessToken, request)
//        LogUtil.printNetworkLog(request, response, "일정 내용 수정")
//        when (response) {
//            is NetworkResult.Success -> {
//
//            }
//            is NetworkResult.Error -> {
//
//            }
//            is NetworkResult.Exception -> {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }

    private suspend fun modifyScheduleMember(moveToBackScreen: () -> Unit) {
//        val accessToken = getAccessTokenUseCase().first()
//        val memberId = getMemberIdUseCase().first()
//        val request = com.whereareyounow.domain.request.schedule.ModifyScheduleMemberRequest(
//            creatorId = memberId,
//            scheduleId = scheduleId,
//            friendIds = _scheduleEditScreenUIState.value.selectedFriendsList.map { it.memberId }
//        )
//        val response = modifyScheduleMemberUseCase(accessToken, request)
//        LogUtil.printNetworkLog(request, response, "일정 멤버 수정")
//        when (response) {
//            is NetworkResult.Success -> {
//                withContext(Dispatchers.Main) {
//                    moveToBackScreen()
//                }
//            }
//            is NetworkResult.Error -> {
//
//            }
//            is NetworkResult.Exception -> {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }
}