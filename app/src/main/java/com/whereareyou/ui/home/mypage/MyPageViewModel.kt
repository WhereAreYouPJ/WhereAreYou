package com.whereareyou.ui.home.mypage

import android.annotation.SuppressLint
import android.app.Application
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyou.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyou.domain.usecase.signin.GetMemberDetailsUseCase
import com.whereareyou.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyou.domain.usecase.signin.ModifyMyInfoUseCase
import com.whereareyou.domain.usecase.signin.SaveAccessTokenUseCase
import com.whereareyou.domain.usecase.signin.SaveMemberIdUseCase
import com.whereareyou.domain.usecase.signin.SaveRefreshTokenUseCase
import com.whereareyou.domain.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            saveRefreshTokenUseCase("")
            saveAccessTokenUseCase("")
            saveMemberIdUseCase("")
            moveToStartScreen()
        }
    }

    @SuppressLint("Recycle", "Range")
    fun updateProfileImage(uri: Uri) {
        viewModelScope.launch {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val cursor = application.contentResolver.query(uri, null, null, null, null)
            cursor?.moveToNext()
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
                when (val updateProfileImageResponse = modifyMyInfoUseCase(accessToken, memberId, it)) {
                    is NetworkResult.Success -> {
                        Log.e("success", "${updateProfileImageResponse.code}, ${updateProfileImageResponse.data}")
                    }

                    is NetworkResult.Error -> {
                        Log.e("error", "${updateProfileImageResponse.code}, ${updateProfileImageResponse.errorData}")
                    }

                    is NetworkResult.Exception -> { Log.e("a exception", "${updateProfileImageResponse.e}") }
                }
            }
        }
    }

    private fun getMyInfo() {
        viewModelScope.launch {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            when (val getMyInfoResponse = getMemberDetailsUseCase(accessToken, memberId)) {
                is NetworkResult.Success -> {
                    Log.e("success", "${getMyInfoResponse.code}, ${getMyInfoResponse.data}")
                    getMyInfoResponse.data?.let { data ->
                        _name.update { data.userName }
                        _email.update { data.email }
                        _profileImageUri.update { data.profileImage }
                    }
                }

                is NetworkResult.Error -> {
                    Log.e("error", "${getMyInfoResponse.code}, ${getMyInfoResponse.errorData}")
                }

                is NetworkResult.Exception -> { Log.e("exception", "${getMyInfoResponse.e}") }
            }
        }
    }

    init {
        getMyInfo()
    }
}