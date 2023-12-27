package com.whereareyounow.ui.home.mypage

import android.annotation.SuppressLint
import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyounow.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberDetailsUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyounow.domain.usecase.signin.ModifyMyInfoUseCase
import com.whereareyounow.domain.usecase.signin.SaveAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.SaveMemberIdUseCase
import com.whereareyounow.domain.usecase.signin.SaveRefreshTokenUseCase
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
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val application: Application,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveMemberIdUseCase: SaveMemberIdUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val modifyMyInfoUseCase: ModifyMyInfoUseCase,
    private val getMemberDetailsUseCase: GetMemberDetailsUseCase

) : AndroidViewModel(application) {

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri: StateFlow<String?> = _imageUri
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email
    private val _profileImageUri = MutableStateFlow<String?>("")
    val profileImageUri: StateFlow<String?> = _profileImageUri

    fun signOut(
        moveToStartScreen: () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            saveRefreshTokenUseCase("")
            saveAccessTokenUseCase("")
            saveMemberIdUseCase("")
            withContext(Dispatchers.Main) { moveToStartScreen() }
        }
    }

    @SuppressLint("Recycle", "Range")
    fun updateProfileImage(uri: Uri) {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val cursor = application.contentResolver.query(uri, null, null, null, null)
            cursor?.moveToNext()
            Log.e("columnCount", "${cursor?.columnNames?.toList()}")
//            Log.e("document_id", "${cursor?.getString(cursor.getColumnIndex("document_id"))}")
            val path = cursor?.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA))
            val file = path?.let { File(it) }
            _imageUri.update {
                path
            }
            Log.e("File", "${file}")
//            val file = File.createTempFile(
//                prefix = "${System.currentTimeMillis()}",
//                suffix = ".jpg",
//                directory = application.cacheDir
//            )
            file?.let {
                val response = modifyMyInfoUseCase(accessToken, memberId, it)
                LogUtil.printNetworkLog(response, "내 정보 수정")
                when (response) {
                    is NetworkResult.Success -> { getMyInfo() }
                    is NetworkResult.Error -> {  }
                    is NetworkResult.Exception -> {  }
                }
            }
        }
    }

    private fun getMyInfo() {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val response = getMemberDetailsUseCase(accessToken, memberId)
            LogUtil.printNetworkLog(response, "내 정보 가져오기")
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { data ->
                        _name.update { data.userName }
                        _email.update { data.email }
                        _profileImageUri.update { data.profileImage }
                    }
                }
                is NetworkResult.Error -> {  }
                is NetworkResult.Exception -> {  }
            }
        }
    }

    init {
        getMyInfo()
    }
}