package com.whereareyounow.ui.main.mypage

import android.annotation.SuppressLint
import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import com.whereareyounow.data.mypage.InfoModificationScreenSideEffect
import com.whereareyounow.data.signup.UserIdState
import com.whereareyounow.data.signup.UserNameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class InfoModificationViewModel @Inject constructor(
    private val application: Application,
//    private val getAccessTokenUseCase: GetAccessTokenUseCase,
//    private val getMemberIdUseCase: GetMemberIdUseCase,
//    private val getMemberDetailsUseCase: GetMemberDetailsUseCase,
//    private val checkIdDuplicateUseCase: CheckIdDuplicateUseCase,
//    private val modifyMyInfoUseCase: ModifyMyInfoUseCase,
) : AndroidViewModel(application) {

    private val nameCondition = Regex("^[\\uAC00-\\uD7A3a-zA-Z]{2,4}\$")
    private val idCondition = Regex("^[a-z][a-z0-9]{3,9}\$")

    private var originalUserName = ""
    private var originalUserId = ""

    private val _inputUserName = MutableStateFlow("")
    val inputUserName: StateFlow<String> = _inputUserName
    private val _inputUserNameState = MutableStateFlow(UserNameState.Satisfied)
    val inputUserNameState: StateFlow<UserNameState> = _inputUserNameState
    private val _inputUserId = MutableStateFlow("")
    val inputUserId: StateFlow<String> = _inputUserId
    private val _inputUserIdState = MutableStateFlow(UserIdState.Satisfied)
    val inputUserIdState: StateFlow<UserIdState> = _inputUserIdState
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    private val _profileImageUri = MutableStateFlow<String?>(null)
    val profileImageUri: StateFlow<String?> = _profileImageUri
    val infoModificationScreenSideEffectFlow = MutableSharedFlow<InfoModificationScreenSideEffect>()

    fun updateInputUserName(name: String) {
        _inputUserName.update { name }
        _inputUserNameState.update {
            if (name == "") {
                UserNameState.Empty
            } else {
                if (name.matches(nameCondition)) UserNameState.Satisfied else UserNameState.Unsatisfied
            }
        }
    }

    fun updateInputUserId(id: String) {
        _inputUserId.update { id }
        _inputUserIdState.update {
            if (id == "") {
                UserIdState.Empty
            } else {
                if (id.matches(idCondition)) UserIdState.Satisfied else UserIdState.Unsatisfied
            }
        }
    }

    fun updateProfileImageUri(uri: Uri) {
        _profileImageUri.update { uri.toString() }
    }

    private fun getUserInfo() {
//        viewModelScope.launch(Dispatchers.Default) {
//            val accessToken = getAccessTokenUseCase().first()
//            val memberId = getMemberIdUseCase().first()
//            val response = getMemberDetailsUseCase(accessToken, memberId)
//            LogUtil.printNetworkLog("memberId = $memberId", response, "내 정보 가져오기")
//            when (response) {
//                is NetworkResult.Success -> {
//                    response.data?.let { data ->
//                        originalUserName = data.userName
//                        originalUserId = data.userId
//                        _inputUserName.update { data.userName }
//                        _inputUserId.update { data.userId }
//                        _email.update { data.email }
//                        _profileImageUri.update { data.profileImage }
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


    fun checkIdDuplicate() {
//        viewModelScope.launch {
//            if (_inputUserId.value == originalUserId) {
//                _inputUserIdState.update { UserIdState.Unique }
//                return@launch
//            }
//            if (_inputUserIdState.value == UserIdState.Satisfied) {
//                val response = checkIdDuplicateUseCase(_inputUserId.value)
//                LogUtil.printNetworkLog("userId = ${_inputUserId.value}", response, "아이디 중복 체크")
//                when (response) {
//                    is NetworkResult.Success -> {
//                        _inputUserIdState.update { UserIdState.Unique }
//                    }
//                    is NetworkResult.Error -> {
//                        _inputUserIdState.update { UserIdState.Duplicated }
//                    }
//                    is NetworkResult.Exception -> {
//                        withContext(Dispatchers.Main) {
//                            Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            } else {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(application, "아이디를 확인해주세요.", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
    }

    @SuppressLint("Range", "Recycle")
    fun saveModifiedInfo(
        moveToBackScreen: () -> Unit
    ) {
//        viewModelScope.launch(Dispatchers.Default) {
//            when (_inputUserIdState.value) {
//                UserIdState.Empty -> {
//                    infoModificationScreenSideEffectFlow.emit(InfoModificationScreenSideEffect.Toast("아이디를 입력해주세요."))
//                    return@launch
//                }
//                UserIdState.Satisfied -> {
//                    if (_inputUserId.value != originalUserId) {
//                        infoModificationScreenSideEffectFlow.emit(InfoModificationScreenSideEffect.Toast("아이디 중복확인을 해주세요."))
//                        return@launch
//                    }
//                }
//                UserIdState.Duplicated -> {
//                    infoModificationScreenSideEffectFlow.emit(InfoModificationScreenSideEffect.Toast("아이디가 중복되었습니다."))
//                    return@launch
//                }
//                UserIdState.Unsatisfied -> {
//                    infoModificationScreenSideEffectFlow.emit(InfoModificationScreenSideEffect.Toast("아이디를 확인해주세요."))
//                    return@launch
//                }
//                UserIdState.Unique -> {}
//            }
//            when (_inputUserNameState.value) {
//                UserNameState.Empty -> {
//                    infoModificationScreenSideEffectFlow.emit(InfoModificationScreenSideEffect.Toast("이름을 입력해주세요."))
//                    return@launch
//                }
//                UserNameState.Unsatisfied -> {
//                    infoModificationScreenSideEffectFlow.emit(InfoModificationScreenSideEffect.Toast("이름을 확인해주세요."))
//                    return@launch
//                }
//                UserNameState.Satisfied -> {}
//            }
//            val accessToken = getAccessTokenUseCase().first()
//            val memberId = getMemberIdUseCase().first()
//            var imageFile: File? = null
//            if (_profileImageUri.value?.contains("content") == true) {
//                val cursor = application.contentResolver.query(_profileImageUri.value!!.toUri(), null, null, null, null)
//                cursor?.moveToNext()
//                val path = cursor?.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA))
//                imageFile = path?.let { File(it) }
//            }
//            if (_inputUserName.value == originalUserName && _inputUserId.value == originalUserId && imageFile == null) {
//                withContext(Dispatchers.Main) {
//                    moveToBackScreen()
//                }
//            } else {
//                val newUserName = if (_inputUserName.value == originalUserName) "" else _inputUserName.value
//                val newUserId = if (_inputUserIdState.value == UserIdState.Unique) _inputUserId.value else ""
//                val response: NetworkResult<Unit> = modifyMyInfoUseCase(accessToken, memberId, imageFile, newUserName, newUserId)
//                LogUtil.printNetworkLog("memberId = $memberId\nimageFile = $imageFile\nnewUserName = $newUserName\nnewUserId = $newUserId", response, "내 정보 수정하기")
//                when (response) {
//                    is NetworkResult.Success -> {
//                        withContext(Dispatchers.Main) {
//                            moveToBackScreen()
//                        }
//                    }
//                    is NetworkResult.Error -> {  }
//                    is NetworkResult.Exception -> {
//                        withContext(Dispatchers.Main) {
//                            Toast.makeText(application, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            }
//        }
    }

    init {
        getUserInfo()
    }
}