package com.whereareyounow.ui.home.schedule.newschedule

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.domain.entity.apimessage.schedule.AddNewScheduleRequest
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.domain.usecase.schedule.AddNewScheduleUseCase
import com.whereareyounow.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private val _appointmentDate = MutableStateFlow("약속 날짜 선택")
    val appointmentDate: StateFlow<String> = _appointmentDate
    private val _appointmentTime = MutableStateFlow("약속 시간 선택")
    val appointmentTime: StateFlow<String> = _appointmentTime

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

    fun updateAppointmentDate(date: String) {
        _appointmentDate.update { date }
    }

    fun updateAppointmentTime(time: String) {
        _appointmentTime.update { time }
    }

    fun updateTitle(title: String) {
        _title.update { title }
    }

    fun clearTitle() {
        _title.update { "" }
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
        if (_appointmentDate.value == "약속 날짜 선택") {
            Toast.makeText(application, "약속 날짜를 선택해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        if (_appointmentTime.value == "약속 시간 선택") {
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
            val body = AddNewScheduleRequest(
                memberId = memberId,
                appointmentTime = _appointmentDate.value + "T" + _appointmentTime.value + ":00",
                title = _title.value,
                place = _destinationName.value,
                memo = _memo.value,
                destinationLatitude = _destinationLatitude.value,
                destinationLongitude = _destinationLongitude.value,
                memberIdList = _friendsList.value.map { friend -> friend.memberId }
            )
            Log.e("friendsList", "${body}")
            val response = addNewScheduleUseCase(accessToken, body)
            LogUtil.printNetworkLog(response, "addNewScheduleUseCase")
            when (response) {
                is NetworkResult.Success -> {
                    withContext(Dispatchers.Main) {
                        moveToCalendarScreen()
                    }
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> {  }
            }
        }
    }

    enum class ScreenState {
        NewSchedule, AddFriends, SearchLocation, Map
    }
}