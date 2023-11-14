package com.whereareyou

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
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
    application: Application
) : AndroidViewModel(application) {

    // 임시 로그인
    private fun signIn() {
        viewModelScope.launch {
            val request = SignInRequest("wnstjd00", "00")
            val signInResult = signInUseCase(request)
            when (signInResult) {
                is NetworkResult.Success -> {
                    Log.e("GlobalViewModel", signInResult.data.toString())
                    saveAccessTokenUseCase("Bearer " + signInResult.data.accessToken)
                    saveMemberIdUseCase(signInResult.data.memberId)
                }
                is NetworkResult.Error -> { Log.e("GlobalViewModel", "error") }
                is NetworkResult.Exception -> { Log.e("GlobalViewModel", "${signInResult.e.message}") }
            }
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            Log.e("GlobalViewModel", "$memberId $accessToken")
        }
    }

    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    fun updateGlobalValue() {

        // 모든 수치는 pixel단위로 저장된다

        val resources = getApplication<Application>().resources
        val metrics = resources.displayMetrics
        val statusBarResourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        // getDimension(): dimen.xml에 정의한 dp값을 기기에 맞게 px로 변환하여 반올림한 값을 int로 반환한다.
        val screenHeight = metrics.heightPixels
        val screenWidth = metrics.widthPixels
        val statusBarHeight = resources.getDimension(statusBarResourceId)
        GlobalValue.screenHeightWithoutStatusBar = screenHeight.toFloat()
        GlobalValue.bottomNavBarHeight = GlobalValue.screenHeightWithoutStatusBar / 15
        GlobalValue.topAppBarHeight = GlobalValue.screenHeightWithoutStatusBar / 15
        GlobalValue.calendarViewHeight = GlobalValue.screenHeightWithoutStatusBar * 26 / 75
        GlobalValue.dailyScheduleViewHeight = GlobalValue.screenHeightWithoutStatusBar * 39 / 75
        GlobalValue.density = metrics.density
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

    fun getLocation() {
        val request = LocationRequest.Builder(10000L)
            .setIntervalMillis(10000L)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.lastOrNull()?.let {
                    LatLng(it.latitude, it.longitude)
                    sendUserLocation(it)
//                    Log.e("location", "$it")
                }
            }
        }
        if (ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        LocationServices.getFusedLocationProviderClient(getApplication<Application>().applicationContext).requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun sendUserLocation(location: Location) {
        viewModelScope.launch {
            val accessToken = getAccessTokenUseCase().first()
            val memberId = getMemberIdUseCase().first()
            val request = SendUserLocationRequest(memberId, location.latitude, location.longitude)
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

    init {
        signIn()
        getLocation()
    }
}