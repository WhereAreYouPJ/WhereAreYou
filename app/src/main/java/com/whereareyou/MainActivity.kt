package com.whereareyou

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.whereareyou.data.GlobalValue
import com.whereareyou.ui.MainNavigation
import com.whereareyou.ui.theme.WhereAreYouTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        Log.e("", "statusBarPadding : ${statusBarHeight}")
        Log.e("", "metrics.widthPixels : $screenHeight $screenWidth")
        Log.e("", "metrics.heightPixels : ${GlobalValue.screenHeightWithoutStatusBar}")
        Log.e("", "metrics.heightPixels : ${GlobalValue.bottomNavBarHeight}")
        Log.e("", "metrics.heightPixels : ${GlobalValue.topAppBarHeight}")
        Log.e("", "metrics.heightPixels : ${GlobalValue.calendarViewHeight}")
        Log.e("", "metrics.heightPixels : ${GlobalValue.dailyScheduleViewHeight}")

        setContent {
            WhereAreYouTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val systemUiController = rememberSystemUiController()

                    systemUiController.setStatusBarColor(
                        color = Color(0xFFFAFAFA),
                        darkIcons = true
                    )
                    MainNavigation()
                }
            }
        }
    }
}

