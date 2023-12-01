package com.whereareyou.ui.home.schedule.detailschedule

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.whereareyou.domain.entity.apimessage.location.GetUserLocationRequest
import com.whereareyou.domain.usecase.location.GetUserLocationUseCase
import com.whereareyou.domain.usecase.schedule.GetDetailScheduleUseCase
import com.whereareyou.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyou.domain.usecase.signin.GetMemberDetailsUseCase
import com.whereareyou.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyou.domain.usecase.signin.ModifyMyInfoUseCase
import com.whereareyou.domain.util.NetworkResult
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
class DetailScheduleViewModel @Inject constructor(
    application: Application,
    private val getMemberDetailsUseCase: GetMemberDetailsUseCase,
    private val getDetailScheduleUseCase: GetDetailScheduleUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val modifyMyInfoUseCase: ModifyMyInfoUseCase,
) : AndroidViewModel(application) {

    private val _screenState = MutableStateFlow(ScreenState.DetailSchedule)
    val screenState: StateFlow<ScreenState> = _screenState

    fun updateScreenState(state: ScreenState) {
        _screenState.update { state }
    }

    private val _scheduleId = MutableStateFlow<String?>(null)
    val scheduleId: StateFlow<String?> = _scheduleId
    private val _creatorId = MutableStateFlow<String>("")
    val creatorId: StateFlow<String> = _creatorId
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title
    private val _startTime = MutableStateFlow("")
    val startTime: StateFlow<String> = _startTime
    private val _endTime = MutableStateFlow("")
    val endTime: StateFlow<String> = _endTime
    private val _place = MutableStateFlow("")
    val place: StateFlow<String> = _place
    private val _memo = MutableStateFlow("")
    val memo: StateFlow<String> = _memo
    private val _destinationLatitude = MutableStateFlow(0.0)
    val destinationLatitude: StateFlow<Double> = _destinationLatitude
    private val _destinationLongitude = MutableStateFlow(0.0)
    val destinationLongitude: StateFlow<Double> = _destinationLongitude
    private val _memberIds = MutableStateFlow(listOf<String>())
    val memberIds: StateFlow<List<String>> = _memberIds
    private val _userInfos = mutableStateListOf<UserInfo>()
    val userInfos: List<UserInfo> = _userInfos
    private val _myLocation = MutableStateFlow(LatLng(0.0, 0.0))
    val myLocation: StateFlow<LatLng> = _myLocation

    fun updateScheduleId(id: String?) {
        _scheduleId.update { id }
        if (id == null) {
            return
        } else {
            getDetailSchedule(id)
        }
    }

    private fun getDetailSchedule(id: String) {
        viewModelScope.launch {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            when (val networkResult = getDetailScheduleUseCase(accessToken, memberId, id)) {
                is NetworkResult.Success -> {
                    networkResult.data?.let { data ->
                        _creatorId.update { data.creatorId }
                        _title.update { data.title }
                        _startTime.update { data.start }
                        _endTime.update { data.end }
                        _place.update { data.place.replace("<b>", "").replace("</b>", "") }
                        _memo.update { data.memo }
                        _destinationLatitude.update { data.destinationLatitude }
                        _destinationLongitude.update { data.destinationLongitude }
                        _memberIds.update { data.friendsIdList }
                        Log.e("getDetailSchedule Success", "${networkResult.data}")
                        getUserLocation()
                    }
                }
                is NetworkResult.Error -> { Log.e("error", "${networkResult.errorData}") }
                is NetworkResult.Exception -> { Log.e("exception", "exception") }
            }
        }
    }

    fun getUserLocation() {
        viewModelScope.launch {
            val memberId = getMemberIdUseCase().first()
            val accessToken = getAccessTokenUseCase().first()
            val userInfoList = mutableListOf<UserInfo>()
            // 유저의 정보를 가져온다.
            for (id in memberIds.value) {
                val userInfo = UserInfo()
                userInfo.memberId = id
                when (val networkResult = getMemberDetailsUseCase(accessToken, id)) {
                    is NetworkResult.Success -> {
                        networkResult.data?.let { data ->
                            userInfo.name = data.userName
                            userInfo.userId = data.userId
                            userInfo.email = data.email
                            userInfo.profileImage = data.profileImage
                            userInfoList.add(userInfo)
                        }

                        Log.e("getMemberDetailsUseCase Success", "${userInfo}")
                    }
                    is NetworkResult.Error -> { Log.e("getMemberDetailsUseCase Error", "${networkResult.code}, ${networkResult.errorData}") }
                    is NetworkResult.Exception -> { Log.e("getMemberDetailsUseCase Exception", "exception") }
                }
            }

            // 유저의 위치를 가져온다.
            val request = GetUserLocationRequest(memberIds.value, _scheduleId.value!!)
            when (val networkResult = getUserLocationUseCase(accessToken, request)) {
                is NetworkResult.Success -> {
                    networkResult.data?.let { data ->
                        for (userInfo in userInfoList) {
                            for (location in data) {
                                // 멤버별 위치 업데이트
                                if (userInfo.memberId == location.memberId) {
                                    userInfo.latitude = location.latitude
                                    userInfo.longitude = location.longitude
                                }
                                // 내 위치 업데이트
                                if (location.memberId == memberId) {
                                    _myLocation.update { LatLng(location.latitude, location.longitude) }
                                }
                            }
                        }
//                        for (idx in 0 until memberIds.value.size) {
//                            if (memberId == memberIds.value[idx]) {
//                                _myLocation.update {
//                                    Log.e("updateMyLocation", "UpdateMyLocation")
//                                    LatLng(
//                                        data[idx].latitude,
//                                        data[idx].longitude
//                                    )
//                                }
//                            }
//                            userInfoList[idx].latitude = data[idx].latitude
//                            userInfoList[idx].longitude = data[idx].longitude
//                        }
                    }
                }
                is NetworkResult.Error -> { Log.e("getUserLocationUseCase Error", "${networkResult.code}, ${networkResult.errorData}") }
                is NetworkResult.Exception -> { Log.e("getUserLocationUseCase Exception", "exception") }
            }
            withContext(Dispatchers.Main) {
                _userInfos.clear()
                _userInfos.addAll(userInfoList)
            }
            Log.e("getMemberDetailsUseCase Success", "${userInfoList}")
        }
    }

    enum class ScreenState {
        DetailSchedule, UserMap
    }

    data class UserInfo(
        var memberId: String = "",
        var name: String = "",
        var userId: String = "",
        var email: String = "",
        var profileImage: String? = "",
        var latitude: Double? = null,
        var longitude: Double? = null
    )
}