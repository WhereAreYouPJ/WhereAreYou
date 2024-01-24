package com.whereareyounow.ui.home.schedule.detailschedule

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.naver.maps.geometry.LatLng
import com.whereareyounow.R
import com.whereareyounow.domain.entity.apimessage.location.GetUserLocationRequest
import com.whereareyounow.domain.entity.apimessage.location.SendUserLocationRequest
import com.whereareyounow.domain.entity.apimessage.schedule.CheckArrivalRequest
import com.whereareyounow.domain.entity.apimessage.schedule.DeleteScheduleRequest
import com.whereareyounow.domain.entity.apimessage.schedule.RefuseOrQuitScheduleRequest
import com.whereareyounow.domain.usecase.location.GetUserLocationUseCase
import com.whereareyounow.domain.usecase.location.SendUserLocationUseCase
import com.whereareyounow.domain.usecase.schedule.CheckArrivalUseCase
import com.whereareyounow.domain.usecase.schedule.DeleteScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.GetDetailScheduleUseCase
import com.whereareyounow.domain.usecase.schedule.RefuseOrQuitScheduleUseCase
import com.whereareyounow.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberDetailsUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.CalendarUtil
import com.whereareyounow.util.LocationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject
import kotlin.math.pow
import kotlin.math.sqrt


@HiltViewModel
class DetailScheduleViewModel @Inject constructor(
    private val application: Application,
    private val getMemberDetailsUseCase: GetMemberDetailsUseCase,
    private val getDetailScheduleUseCase: GetDetailScheduleUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val sendUserLocationUseCase: SendUserLocationUseCase,
    private val locationUtil: LocationUtil,
    private val refuseOrQuitScheduleUseCase: RefuseOrQuitScheduleUseCase,
    private val deleteScheduleUseCase: DeleteScheduleUseCase,
    private val checkArrivalUseCase: CheckArrivalUseCase,
) : AndroidViewModel(application) {

    private val _screenState = MutableStateFlow(ScreenState.DetailSchedule)
    val screenState: StateFlow<ScreenState> = _screenState

    fun updateScreenState(state: ScreenState) {
        _screenState.update { state }
    }

    private val _scheduleId = MutableStateFlow("")
    val scheduleId: StateFlow<String> = _scheduleId
    private val _creatorId = MutableStateFlow("")
    val creatorId: StateFlow<String> = _creatorId
    private val _scheduleName = MutableStateFlow("")
    val scheduleName: StateFlow<String> = _scheduleName
    private val _scheduleYear = MutableStateFlow("")
    val scheduleYear: StateFlow<String> = _scheduleYear
    private val _scheduleMonth = MutableStateFlow("0")
    val scheduleMonth: StateFlow<String> = _scheduleMonth
    private val _scheduleDate = MutableStateFlow("")
    val scheduleDate: StateFlow<String> = _scheduleDate
    private val _scheduleDayOfWeek = MutableStateFlow("")
    val scheduleDayOfWeek: StateFlow<String> = _scheduleDayOfWeek
    private val _scheduleHour = MutableStateFlow("")
    val scheduleHour: StateFlow<String> = _scheduleHour
    private val _scheduleMinute = MutableStateFlow("")
    val scheduleMinute: StateFlow<String> = _scheduleMinute
    private val _destinationName = MutableStateFlow("")
    val destinationName: StateFlow<String> = _destinationName
    private val _destinationAddress = MutableStateFlow("")
    val destinationAddress: StateFlow<String> = _destinationAddress
    private val _memo = MutableStateFlow("")
    val memo: StateFlow<String> = _memo
    private val _destinationLatitude = MutableStateFlow(0.0)
    val destinationLatitude: StateFlow<Double> = _destinationLatitude
    private val _destinationLongitude = MutableStateFlow(0.0)
    val destinationLongitude: StateFlow<Double> = _destinationLongitude
    private val _memberIds = mutableStateListOf<String>()
    private val _memberInfos = mutableStateListOf<MemberInfo>()
    val memberInfos: List<MemberInfo> = _memberInfos
    private val _myLocation = MutableStateFlow(LatLng(0.0, 0.0))
    val myLocation: StateFlow<LatLng> = _myLocation
    private val _isLocationCheckEnabled = MutableStateFlow(false)
    val isLocationCheckEnabled: StateFlow<Boolean> = _isLocationCheckEnabled
    private val _isScheduleCreator = MutableStateFlow(false)
    val isScheduleCreator: StateFlow<Boolean> = _isScheduleCreator

    fun updateScheduleId(id: String) {
        _scheduleId.update { id }
        getDetailSchedule(id)
    }

    private fun getDetailSchedule(id: String) {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val response = getDetailScheduleUseCase(accessToken, memberId, id)
            LogUtil.printNetworkLog(response, "일정 상세 정보 가져오기")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        _isScheduleCreator.update {
                            data.creatorId == memberId
                        }
                        _creatorId.update { data.creatorId }
                        _scheduleName.update { data.title }
                        _scheduleYear.update { data.appointmentTime.split("T")[0].split("-")[0] }
                        _scheduleMonth.update { data.appointmentTime.split("T")[0].split("-")[1] }
                        _scheduleDate.update { data.appointmentTime.split("T")[0].split("-")[2] }
                        _scheduleDayOfWeek.update {
                            when (CalendarUtil.getDayOfWeek(_scheduleYear.value.toInt(), _scheduleMonth.value.toInt(), _scheduleDate.value.toInt())) {
                                1 -> "일"
                                2 -> "월"
                                3 -> "화"
                                4 -> "수"
                                5 -> "목"
                                6 -> "금"
                                else -> "토"
                            }
                        }
                        _scheduleHour.update { data.appointmentTime.split("T")[1].split(":")[0] }
                        _scheduleMinute.update { data.appointmentTime.split("T")[1].split(":")[1] }
                        _destinationName.update { data.place.replace("<b>", "").replace("</b>", "") }
                        _destinationAddress.update { data.roadName }
                        _memo.update { data.memo }
                        _destinationLatitude.update { data.destinationLatitude }
                        _destinationLongitude.update { data.destinationLongitude }
                        _memberIds.clear()
                        _memberIds.addAll(data.friendsIdList)
                        _isLocationCheckEnabled.update { CalendarUtil.getMinuteDiffWithCurrentTime(data.appointmentTime.replace("T", " ")) <= 60 }
                        getUserInfo(data.arrivedFriendsIdList)
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

    private fun getUserInfo(arrivedFriendIdsList: List<String>) {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val userInfoList = mutableListOf<MemberInfo>()
            // 유저의 정보를 가져온다.
            for (id in _memberIds) {
                val userInfo = MemberInfo()
                userInfo.memberId = id
                val response = getMemberDetailsUseCase(accessToken, id)
                LogUtil.printNetworkLog(response, "유저 정보 가져오기")
                when (response) {
                    is NetworkResult.Success -> {
                        response.data?.let { data ->
                            userInfo.name = data.userName
                            userInfo.userId = data.userId
                            userInfo.email = data.email
                            userInfo.profileImage = data.profileImage
                            if (userInfo.memberId in arrivedFriendIdsList) {
                                userInfo.isArrived = true
                            }
                            if (userInfo.profileImage != null) {
                                val connection = URL(userInfo.profileImage).openConnection().apply {
                                    doInput = true
                                    connect()
                                }
                                val input = connection.getInputStream()
                                val bitmap = BitmapFactory.decodeStream(input)
                                userInfo.imageBitmap = bitmap.getCircledBitmap(application.applicationContext, (application.resources.displayMetrics.density * 40).toInt())
                            }
                            userInfoList.add(userInfo)
                        }
                    }
                    is NetworkResult.Error -> {}
                    is NetworkResult.Exception -> {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            withContext(Dispatchers.Main) {
                _memberInfos.clear()
                _memberInfos.addAll(
                    userInfoList.sortedWith(
                        compareBy(
                            { !it.isArrived },
                            { it.name }
                        )
                    )
                )
                getUsersLocation()
            }
        }
    }

    fun getUsersLocation() {
        viewModelScope.launch(Dispatchers.Default) {
            val memberId = getMemberIdUseCase().first()
            val accessToken = getAccessTokenUseCase().first()
            // 유저의 위치를 가져온다.
            val request = GetUserLocationRequest(_memberIds, _scheduleId.value!!)
            val response = getUserLocationUseCase(accessToken, request)
            LogUtil.printNetworkLog(response, "유저 위치 가져오기")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        for (userInfo in _memberInfos) {
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

    fun sendUserLocation() {
        locationUtil.getCurrentLocation {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val request = SendUserLocationRequest(memberId, it.latitude, it.longitude)
            val response = sendUserLocationUseCase(accessToken, request)
            LogUtil.printNetworkLog(response, "유저 위치 전송하기")
            when (response) {
                is NetworkResult.Success -> {
                    getUsersLocation()
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            Log.e("location", "${sqrt((it.latitude - _destinationLatitude.value).pow(2.0) + (it.longitude - _destinationLongitude.value).pow(2.0))}")
            if (sqrt((it.latitude - _destinationLatitude.value).pow(2.0) + (it.longitude - _destinationLongitude.value).pow(2.0)) < 0.00335) {
                checkArrival()
            }
        }
    }

    private fun checkArrival() {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val request = CheckArrivalRequest(memberId, _scheduleId.value)
            val response = checkArrivalUseCase(accessToken, request)
            LogUtil.printNetworkLog(response, "도착 여부")
            when (response) {
                is NetworkResult.Success -> {

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

    fun quitSchedule(
        moveToBackScreen: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val request = RefuseOrQuitScheduleRequest(memberId, _scheduleId.value)
            val response = refuseOrQuitScheduleUseCase(accessToken, request)
            LogUtil.printNetworkLog(response, "일정 나가기")
            when (response) {
                is NetworkResult.Success -> {
                    withContext(Dispatchers.Main) { moveToBackScreen() }
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

    fun deleteSchedule(
        moveToBackScreen: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val request = DeleteScheduleRequest(memberId, _scheduleId.value)
            val response = deleteScheduleUseCase(accessToken, request)
            LogUtil.printNetworkLog(response, "일정 삭제하기")
            when (response) {
                is NetworkResult.Success -> {
                    withContext(Dispatchers.Main) { moveToBackScreen() }
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

    enum class ScreenState {
        DetailSchedule, ModifySchedule, UserMap
    }

    data class MemberInfo(
        var memberId: String = "",
        var name: String = "",
        var userId: String = "",
        var email: String = "",
        var profileImage: String? = "",
        var imageBitmap: Bitmap? = null,
        var isArrived: Boolean = false,
        var latitude: Double? = null,
        var longitude: Double? = null
    )
}

fun Bitmap.getCircledBitmap(context: Context, size: Int): Bitmap {
    val croppedBitmap =
        if (this.width == this.height) {
            this
        } else if (this.width > this.height) {
            val horizontalCenter = this.width / 2
            val verticalCenter = this.height / 2
            Bitmap.createBitmap(this, horizontalCenter - verticalCenter, 0, verticalCenter * 2, verticalCenter * 2)
        } else {
            val horizontalCenter = this.width / 2
            val verticalCenter = this.height / 2
            Bitmap.createBitmap(this, 0, verticalCenter - horizontalCenter, horizontalCenter * 2, horizontalCenter * 2)
        }

    val locationBackground = ContextCompat.getDrawable(context, R.drawable.location_background)!!
    val backgroundOutput = Bitmap.createBitmap(size, ((size.toFloat() / locationBackground.intrinsicWidth) * locationBackground.intrinsicHeight).toInt(), Bitmap.Config.ARGB_8888)
    val rect = Rect(0, 0, size, ((size.toFloat() / locationBackground.intrinsicWidth) * locationBackground.intrinsicHeight).toInt())
    val mainCanvas = Canvas(backgroundOutput)
    locationBackground.setBounds(0, 0, mainCanvas.width, mainCanvas.height)
    locationBackground.draw(mainCanvas)


    val resizedBitmap = Bitmap.createScaledBitmap(croppedBitmap, size, size, false)
    val output = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    val paint = Paint()
    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    canvas.drawCircle(size / 2f, size / 2f, (size - 10) / 2f, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(resizedBitmap, rect, rect, paint)
    mainCanvas.drawBitmap(output, rect, rect, null)

    return backgroundOutput
}