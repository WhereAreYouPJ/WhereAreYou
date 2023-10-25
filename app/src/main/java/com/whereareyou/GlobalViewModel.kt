package com.whereareyou

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.whereareyou.data.GlobalValue
import com.whereareyou.repository.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GlobalViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    application: Application
) : AndroidViewModel(application) {


    fun setMyMemberId(myId: String) {
        viewModelScope.launch {
            sharedPreferencesRepository.updateMyMemberId(myId)
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
}