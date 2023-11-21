package com.whereareyou.ui.home.schedule.detailschedule

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.whereareyou.domain.usecase.location.GetUserLocationUseCase
import com.whereareyou.domain.usecase.schedule.GetDetailScheduleUseCase
import com.whereareyou.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyou.domain.usecase.signin.GetMemberDetailsUseCase
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
class DetailScheduleViewModel @Inject constructor(
    application: Application,
    private val getMemberDetailsUseCase: GetMemberDetailsUseCase,
    private val getDetailScheduleUseCase: GetDetailScheduleUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase,
) : AndroidViewModel(application) {

    private val _screenState = MutableStateFlow(ScreenState.DetailSchedule)
    val screenState: StateFlow<ScreenState> = _screenState

    fun updateScreenState(state: ScreenState) {
        _screenState.update { state }
    }

    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title
    private val _start = MutableStateFlow("")
    val start: StateFlow<String> = _start
    private val _end = MutableStateFlow("")
    val end: StateFlow<String> = _end
    private val _place = MutableStateFlow("")
    val place: StateFlow<String> = _place
    private val _memo = MutableStateFlow("")
    val memo: StateFlow<String> = _memo
    private val _memberIds = MutableStateFlow(listOf<String>())
    val memberIds: StateFlow<List<String>> = _memberIds
    private val _userInfos = MutableStateFlow(listOf<UserInfo>())
    val userInfos: StateFlow<List<UserInfo>> = _userInfos
    private val _myLocation = MutableStateFlow(LatLng(0.0, 0.0))
    val myLocation: StateFlow<LatLng> = _myLocation

    fun updateScheduleId(id: String?) {
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
            val getDetailScheduleResult = getDetailScheduleUseCase(accessToken, memberId, id)
            when (getDetailScheduleResult) {
                is NetworkResult.Success -> {
                    if (getDetailScheduleResult.data != null) {
                        Log.e("success", "${getDetailScheduleResult.data}")
                        val detailSchedule = getDetailScheduleResult.data!!
                        _title.update { detailSchedule.title }
                        _start.update { detailSchedule.start }
                        _end.update { detailSchedule.end }
                        _place.update { detailSchedule.place }
                        _memo.update { detailSchedule.memo }
                        _memberIds.update { detailSchedule.friendsIdList }
                    }
                }
                is NetworkResult.Error -> { Log.e("error", "${getDetailScheduleResult.errorData}") }
                is NetworkResult.Exception -> { Log.e("exception", "exception") }
            }
            getUserLocation()
        }
    }

    private fun getUserLocation() {
        viewModelScope.launch {
            val memberId = getMemberIdUseCase().first()
            val accessToken = getAccessTokenUseCase().first()
            for (id in memberIds.value) {
                val userInfo = UserInfo()
                val userInfoResult = getMemberDetailsUseCase(accessToken, id)
                when (userInfoResult) {
                    is NetworkResult.Success -> {
                        if (userInfoResult.data != null) {
                            userInfo.name = userInfoResult.data!!.userName
                            userInfo.userId = userInfoResult.data!!.userId
                            userInfo.email = userInfoResult.data!!.email
                            userInfo.profileImage = userInfoResult.data!!.profileImage
                        }

                        Log.e("success", "${userInfo}")
                    }
                    is NetworkResult.Error -> { Log.e("error", "${userInfoResult.code}, ${userInfoResult.errorData}") }
                    is NetworkResult.Exception -> { Log.e("exception", "exception") }
                }

                val response = getUserLocationUseCase(accessToken, id)
                when (response) {
                    is NetworkResult.Success -> {
                        if (response.data != null) {
                            userInfo.latitude = response.data!!.latitude
                            userInfo.longitude = response.data!!.longitude
                        }
                        if (userInfo.userId == "user1" && userInfo.latitude != null && userInfo.longitude != null) {
                            _myLocation.update { LatLng(userInfo.latitude!!, userInfo.longitude!!) }
                        }
                        Log.e("success", "${userInfo}")
                    }
                    is NetworkResult.Error -> { Log.e("error", "${response.code}, ${response.errorData}") }
                    is NetworkResult.Exception -> { Log.e("exception", "exception") }
                }
                _userInfos.update {
                    it + userInfo
                }
            }
        }
    }

    enum class ScreenState {
        DetailSchedule, UserMap
    }

    data class UserInfo(
        var name: String = "",
        var userId: String = "",
        var email: String = "",
        var profileImage: String? = "",
        var latitude: Double? = null,
        var longitude: Double? = null
    )
}