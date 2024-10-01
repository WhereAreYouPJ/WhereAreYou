package com.whereareyounow.ui.main.mypage

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.domain.request.member.GetUserInfoByMemberSeqRequest
import com.whereareyounow.domain.request.member.SendEmailCodeRequest
import com.whereareyounow.domain.request.member.VerifyEmailCodeRequest
import com.whereareyounow.domain.usecase.location.DeleteFavoriteLocationUsecase
import com.whereareyounow.domain.usecase.location.GetFavoriteLocationUseCase
import com.whereareyounow.domain.usecase.member.GetUserInfoByMemberSeqUseCase
import com.whereareyounow.domain.usecase.member.SendEmailCodeUseCase
import com.whereareyounow.domain.usecase.member.VerifyEmailCodeUseCase
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.domain.util.onError
import com.whereareyounow.domain.util.onException
import com.whereareyounow.domain.util.onSuccess
import com.whereareyounow.ui.main.mypage.mapper.toModel
import com.whereareyounow.ui.main.mypage.model.LocationFaboriteInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val application: Application,
//    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
//    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
//    private val saveMemberIdUseCase: SaveMemberIdUseCase,
//    private val getAccessTokenUseCase: GetAccessTokenUseCase,
//    private val getMemberIdUseCase: GetMemberIdUseCase,
//    private val getMemberDetailsUseCase: GetMemberDetailsUseCase,
//    private val deleteMemberUseCase: DeleteMemberUseCase,
//    private val resetCalendarUseCase: ResetCalendarUseCase,
    private val getUserInfoByMemberSeqUseCase: GetUserInfoByMemberSeqUseCase,
    private val verifyEmailCodeUseCase: VerifyEmailCodeUseCase,
    private val sendEmailCodeUseCase: SendEmailCodeUseCase,
    private val getFaboriteLocationUseCase: GetFavoriteLocationUseCase,
    private val deleteFavoriteLocacionUseCase : DeleteFavoriteLocationUsecase
) : AndroidViewModel(application) {

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri: StateFlow<String?> = _imageUri
    private val _name = MutableStateFlow<String?>("")
    val name: StateFlow<String?> = _name
    private val _email = MutableStateFlow<String?>("")
    val email: StateFlow<String?> = _email
    private val _profileImageUri =
        MutableStateFlow<String?>("https://m.segye.com/content/image/2021/11/16/20211116509557.jpg")
    val profileImageUri: StateFlow<String?> = _profileImageUri
    private val _isVerifyed = MutableStateFlow<String?>("FALSE")
    val isVerifyed: StateFlow<String?> = _isVerifyed


    private val _locationSeq = MutableStateFlow<Int?>(0)
    val locationSeq: StateFlow<Int?> = _locationSeq
    private val _location = MutableStateFlow<String?>("")
    val location: StateFlow<String?> = _location
    private val _streetName = MutableStateFlow<String?>("")
    val streetName: StateFlow<String?> = _streetName
    private val _locationFaboriteList = MutableStateFlow<List<LocationFaboriteInfoModel?>>(emptyList())
//    private val _locationFaboriteList = MutableStateFlow<List<LocationFaboriteInfoModel?>>(
//        listOf(
//            LocationFaboriteInfoModel(locationSeq = 0 , location = "서울대학교0" , streetName = "서울대학교닷거리1"),
//            LocationFaboriteInfoModel(locationSeq = 0 , location = "서울대학교1" , streetName = "서울대학교닷거리2"),
//            LocationFaboriteInfoModel(locationSeq = 0 , location = "서울대학교2" , streetName = "서울대학교닷거리3"),
//            LocationFaboriteInfoModel(locationSeq = 0 , location = "서울대학교3" , streetName = "서울대학교닷거리4"),
//            LocationFaboriteInfoModel(locationSeq = 0 , location = "서울대학교4" , streetName = "서울대학교닷거리5"),
//            LocationFaboriteInfoModel(locationSeq = 0 , location = "서울대학교5" , streetName = "서울대학교닷거리6"),
//        )
//    )
    val locationFaboriteList : StateFlow<List<LocationFaboriteInfoModel?>> = _locationFaboriteList
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    init {
        getMyInfo()
        getLocationFaborite(
            1
        )
//        getLocationFaborite()
    }

    data class Announcement(
        val string: String
    )

    private val _announcementList = MutableStateFlow<List<Announcement>>(emptyList())
    val announcementList: StateFlow<List<Announcement>> = _announcementList.asStateFlow()

    fun signOut(
        moveToStartScreen: () -> Unit,
    ) {
//        viewModelScope.launch(Dispatchers.Default) {
//            saveRefreshTokenUseCase("")
//            saveAccessTokenUseCase("")
//            saveMemberIdUseCase("")
//            withContext(Dispatchers.Main) { moveToStartScreen() }
//        }
    }

    fun getMyInfo() {

        viewModelScope.launch(Dispatchers.IO) {
//            val accessToken = getAccessTokenUseCase().first()
//            val memberId = getMemberIdUseCase().first()
//            val response = getMemberDetailsUseCase(accessToken, memberId)
//            LogUtil.printNetworkLog("memberId = $memberId", response, "내 정보 가져오기")

            getUserInfoByMemberSeqUseCase(GetUserInfoByMemberSeqRequest(memberSeq = 1)).collect {
//
//
                when (it) {

                    is NetworkResult.Success -> {
                        val userInfo = it.data?.toModel()
                        userInfo?.let {
                            _name.value = it.userName
                            _email.value = it.email
                            _profileImageUri.value = it.profileImage
                        }
                    }

                    is NetworkResult.Error -> {


                    }

                    is NetworkResult.Exception -> {


                    }
                }

            }

        }
    }

    fun emailRequest(email: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sendEmailCodeUseCase(
                SendEmailCodeRequest(
                    email = email
                )
            ).collect { response ->
                when (response) {

                    is NetworkResult.Success -> {


                    }

                    is NetworkResult.Error -> {


                    }

                    is NetworkResult.Exception -> {


                    }
                }
            }

        }

    }

    fun emailVerify(email: String, code: String) {
        viewModelScope.launch(Dispatchers.IO) {

            verifyEmailCodeUseCase(
                VerifyEmailCodeRequest(
                    email = email,
                    code = code
                )
            ).onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    data?.let {
                        _isVerifyed.value = data
                    }
                }.onError { code, message ->

                }.onException { }
            }
                .catch {
                    LogUtil.e(
                        "flow error",
                        "VerifyEmailCodeUseCase\n${it.message}\n${it.stackTrace}"
                    )
                }
                .launchIn(viewModelScope)
        }
    }

    fun withdrawAccount(
        moveToStartScreen: () -> Unit,
    ) {
//        viewModelScope.launch(Dispatchers.Default) {
//            val accessToken = getAccessTokenUseCase().first()
//            val memberId = getMemberIdUseCase().first()
//            val request = com.whereareyounow.domain.request.signin.DeleteMemberRequest(memberId)
//            val response = deleteMemberUseCase(accessToken, request)
//            LogUtil.printNetworkLog(request, response, "회원 탈퇴하기")
//            when (response) {
//                is NetworkResult.Success -> {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(application, "회원탈퇴가 완료되었습니다.", Toast.LENGTH_SHORT).show()
//                        moveToStartScreen()
//                    }
//                }
//                is NetworkResult.Error -> {  }
//                is NetworkResult.Exception -> {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }
    }

    fun deleteCalendar() {
//        viewModelScope.launch(Dispatchers.Default) {
//            val accessToken = getAccessTokenUseCase().first()
//            val memberId = getMemberIdUseCase().first()
//            val request =
//                com.whereareyounow.domain.request.schedule.ResetCalendarRequest(memberId)
//            val response = resetCalendarUseCase(accessToken, request)
//            LogUtil.printNetworkLog(request, response, "캘린더 삭제")
//            when (response) {
//                is NetworkResult.Success -> {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(application, "캘린더가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                is NetworkResult.Error -> {  }
//                is NetworkResult.Exception -> {
//                    withContext(Dispatchers.Main) {
//                        Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        }
    }




    private fun getLocationFaborite(memberSeq : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getFaboriteLocationUseCase(
                memberSeq = memberSeq
            ).onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    Log.d("getLocationFaborite" , code.toString())
                    Log.d("getLocationFaborite" , message.toString())
                    Log.d("getLocationFaborite" , data.toString())

                    val updatedList = data?.map { it.toModel() } ?: emptyList()
                    _locationFaboriteList.value = updatedList.toList()

                    _isLoading.value = false

                }.onError { code, message ->
                    Log.d("getLocationFaborite" , code.toString())
                    Log.d("getLocationFaborite" , message.toString())

                }.onException { }
            }
                .catch {
                    LogUtil.e(
                        "flow error",
                        "getFaboriteLocationUseCase\n${it.message}\n${it.stackTrace}"
                    )
                }
                .launchIn(this)

        }

    }

    fun DeleteFavoriteLocation(
        memberSeq : Int,
        locationSeqs : List<Int>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteFavoriteLocacionUseCase(
                memberSeq = memberSeq,
                locationSeqs = locationSeqs
            ).onEach { networkResult ->
                networkResult.onSuccess { code, message, data ->
                    Log.d("getLocationFaborite" , code.toString())
                    Log.d("getLocationFaborite" , message.toString())
                    Log.d("getLocationFaborite" , data.toString())

                }.onError { code, message ->
                    Log.d("getLocationFaborite" , code.toString())
                    Log.d("getLocationFaborite" , message.toString())

                }.onException { }
            }
                .catch {
                    LogUtil.e(
                        "flow error",
                        "getFaboriteLocationUseCase\n${it.message}\n${it.stackTrace}"
                    )
                }
                .launchIn(this)
        }
    }

}


