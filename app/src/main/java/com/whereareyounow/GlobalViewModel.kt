package com.whereareyounow

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.whereareyounow.domain.entity.apimessage.location.SendUserLocationRequest
import com.whereareyounow.domain.entity.apimessage.signin.SignInRequest
import com.whereareyounow.domain.usecase.location.SendUserLocationUseCase
import com.whereareyounow.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyounow.domain.usecase.signin.SaveAccessTokenUseCase
import com.whereareyounow.domain.usecase.signin.SaveMemberIdUseCase
import com.whereareyounow.domain.usecase.signin.SignInUseCase
import com.whereareyounow.domain.util.NetworkResult
import com.whereareyounow.util.Coordinate
import com.whereareyounow.util.LocationUtil
import com.whereareyounow.util.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class GlobalViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val saveMemberIdUseCase: SaveMemberIdUseCase,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    private val sendUserLocationUseCase: SendUserLocationUseCase,
    private val locationUtil: LocationUtil,
    private val application: Application
) : AndroidViewModel(application) {

    private var currentLocation = Coordinate(0.0, 0.0)
    // 임시 로그인
    private fun signIn() {
        viewModelScope.launch(Dispatchers.Default) {
            val request = SignInRequest("user1", "00")
            val signInResult = signInUseCase(request)
            when (signInResult) {
                is NetworkResult.Success -> {
                    if (signInResult.data != null) {
                        Log.e("GlobalViewModel", signInResult.data.toString())
                        saveAccessTokenUseCase("Bearer " + signInResult.data!!.accessToken)
                        saveMemberIdUseCase(signInResult.data!!.memberId)
                    }
                }
                is NetworkResult.Error -> { Log.e("GlobalViewModel", "error") }
                is NetworkResult.Exception -> { Log.e("GlobalViewModel", "${signInResult.e.message}") }
            }
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            Log.e("GlobalViewModel", "$memberId $accessToken")
        }
    }

    fun checkEnvironment(
        moveToSignInScreen: () -> Unit,
        moveToMainScreen: () -> Unit,
        locationPermissionRequest: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.Default) {
            // 권한 확인


            // 인터넷 연결상태 확인


            // 로그인 상태 확인


        }
    }

    fun checkIsSignedIn(): Boolean {
        var isSignedIn = false
        runBlocking {
            delay(2000)
            if (getMemberIdUseCase().first().isNotEmpty()) {
                isSignedIn = false
            }
        }
        return isSignedIn
    }

    private fun getLocation() {
        viewModelScope.launch(Dispatchers.Default) {
            while (true) {
                delay(20000)
//                Log.e("GlobalViewModel", "${currentLocation.latitude}, ${currentLocation.longitude}")
                sendUserLocation(currentLocation.latitude, currentLocation.longitude)
            }
        }
    }

    fun sendUserLocation(lat: Double, lng: Double) {
        viewModelScope.launch(Dispatchers.Default) {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val request = SendUserLocationRequest(memberId, lat, lng)
            val result = sendUserLocationUseCase(accessToken, request)
            when (result) {
                is NetworkResult.Success -> {
//                    Log.e("GlobalViewModel", result.data.toString())
                }
                is NetworkResult.Error -> { Log.e("GlobalViewModel", "error") }
                is NetworkResult.Exception -> { Log.e("GlobalViewModel", "${result.e.message}") }
            }
        }
    }

    fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("GlobalViewModel-token", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result
            Log.e("GlobalViewModel-token", token)

            val msg = token.toString()
            Toast.makeText(application, msg, Toast.LENGTH_SHORT).show()
        })
    }

    fun checkNetworkState(
        checkLocationPermission: () -> Unit,
    ): Boolean {
        if (NetworkManager.checkNetworkState()) {
//            checkLo
        } else {

        }
        return false
    }

    init {
//        locationUtil.getCurrentLocation(latLng = currentLocation)
//        signIn()
//        getLocation()
        getToken()
//        checkNetworkState()
    }
}