package com.whereareyounow.ui.main.schedule.scheduleedit

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.data.cached.FriendList
import com.whereareyounow.data.detailschedule.DetailScheduleScreenUIState
import com.whereareyounow.data.scheduleedit.ScheduleEditScreenSideEffect
import com.whereareyounow.data.scheduleedit.ScheduleEditScreenUIState
import com.whereareyounow.globalvalue.type.ScheduleColor
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
    private val _uiState = MutableStateFlow(ScheduleEditScreenUIState())
    var isInitialized = false
    val uiState = _uiState.asStateFlow()
    val sideEffectFlow = MutableSharedFlow<ScheduleEditScreenSideEffect>()

    fun updateScheduleId(id: String) {
        scheduleId = id
    }

    fun updateSelectedFriendsList(friendIds: List<Int>) {
        _uiState.update {
            it.copy(selectedFriendsList = FriendList.list.filter { friend ->
                friend.memberSeq in friendIds })
        }
    }

    fun updateScheduleName(name: String) {
        _uiState.update {
            it.copy(scheduleName = name)
        }
    }

    fun toggleAllDay() {
        _uiState.update {
            it.copy(isAllDay = !it.isAllDay)
        }
    }

    fun updateStartDate(year: Int, month: Int, date: Int) {
        _uiState.update {
            it.copy(
                startYear = year,
                startMonth = month,
                startDate = date
            )
        }
    }

    fun updateStartTime(hour: Int, minute: Int) {
        _uiState.update {
            it.copy(
                startHour = hour,
                startMinute = minute
            )
        }
    }

    fun updateEndDate(year: Int, month: Int, date: Int) {
        _uiState.update {
            it.copy(
                endYear = year,
                endMonth = month,
                endDate = date
            )
        }
    }

    fun updateEndTime(hour: Int, minute: Int) {
        _uiState.update {
            it.copy(
                endHour = hour,
                endMinute = minute
            )
        }
    }

    fun updateDestinationInformation(name: String, address: String, lat: Double, lng: Double) {
        _uiState.update {
            it.copy(
                destinationName = name,
                destinationAddress = address,
                destinationLatitude = lat,
                destinationLongitude = lng
            )
        }
    }

    fun updateMemo(memo: String) {
        _uiState.update {
            it.copy(memo = memo)
        }
    }

    fun updateDestinationLocation(lat: Double, lng: Double) {
        _uiState.update {
            it.copy(
                destinationLatitude = lat,
                destinationLongitude = lng
            )
        }
    }

    fun updateScheduleColor(color: ScheduleColor) {
        _uiState.update {
            it.copy(
                color = color
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
            if (_uiState.value.scheduleName == "") {
                sideEffectFlow.emit(ScheduleEditScreenSideEffect.Toast("일정명을 입력해주세요."))
                return@launch
            }
            if (_uiState.value.startDate == 0) {
                sideEffectFlow.emit(ScheduleEditScreenSideEffect.Toast("약속 날짜를 선택해주세요."))
                Toast.makeText(application, "", Toast.LENGTH_SHORT).show()
                return@launch
            }
            if (_uiState.value.destinationName == "") {
                sideEffectFlow.emit(ScheduleEditScreenSideEffect.Toast("목적지를 선택해주세요."))
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