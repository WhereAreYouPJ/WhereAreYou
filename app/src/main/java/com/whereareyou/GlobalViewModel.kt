package com.whereareyou

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.naver.maps.geometry.LatLng
import com.whereareyou.data.GlobalValue
import com.whereareyou.domain.entity.apimessage.location.SendUserLocationRequest
import com.whereareyou.domain.entity.apimessage.signin.SignInRequest
import com.whereareyou.domain.usecase.location.SendUserLocationUseCase
import com.whereareyou.domain.usecase.signin.GetAccessTokenUseCase
import com.whereareyou.domain.usecase.signin.GetMemberIdUseCase
import com.whereareyou.domain.usecase.signin.SaveAccessTokenUseCase
import com.whereareyou.domain.usecase.signin.SaveMemberIdUseCase
import com.whereareyou.domain.usecase.signin.SignInUseCase
import com.whereareyou.domain.util.NetworkResult
import com.whereareyou.repository.SharedPreferencesRepository
import com.whereareyou.util.CalendarUtil
import com.whereareyou.util.Coordinate
import com.whereareyou.util.LocationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
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
        viewModelScope.launch {
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

    fun checkIsSignedIn(): Boolean {
        var isSignedIn = false
        runBlocking {
            delay(2000)
            if (getMemberIdUseCase().first().isNotEmpty()) {
                isSignedIn = true
            }
        }
        return isSignedIn
    }

    private fun getLocation() {
        viewModelScope.launch {
            while (true) {
                delay(20000)
                Log.e("GlobalViewModel", "${currentLocation.latitude}, ${currentLocation.longitude}")
                sendUserLocation(currentLocation.latitude, currentLocation.longitude)
            }
        }
    }

    fun sendUserLocation(lat: Double, lng: Double) {
        viewModelScope.launch {
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

    init {
        locationUtil.getCurrentLocation(latLng = currentLocation)
        signIn()
        getLocation()
        getToken()
    }
}