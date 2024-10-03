package com.whereareyounow

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.whereareyounow.data.globalvalue.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.whereareyounow.data.globalvalue.CALENDAR_VIEW_HEIGHT
import com.whereareyounow.data.globalvalue.DAILY_BRIEF_SCHEDULE_VIEW_HEIGHT
import com.whereareyounow.data.globalvalue.SCREEN_HEIGHT_WITHOUT_SYSTEM_BAR
import com.whereareyounow.data.globalvalue.SCREEN_WIDTH
import com.whereareyounow.data.globalvalue.SYSTEM_STATUS_BAR_HEIGHT
import com.whereareyounow.data.globalvalue.SYSTEM_NAVIGATION_BAR_HEIGHT
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.data.globalvalue.TOTAL_SCREEN_HEIGHT
import com.whereareyounow.ui.MainNavigation
import com.whereareyounow.ui.theme.OnMyWayTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: GlobalViewModel by lazy {
        ViewModelProvider(this)[GlobalViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getToken()

        enableEdgeToEdge()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
            OnMyWayTheme {
                val density = LocalDensity.current.density
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .onGloballyPositioned {
                            updateGlobalValue(it.size, density)
                        },
                    color = Color(0xFFFFFFFF)
                ) {
                    MainNavigation()
                }
            }
        }
    }

    // 모든 영역의 단위는 기본적으로 dp.
    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    private fun updateGlobalValue(size: IntSize, density: Float) {
        val resources = application.resources
        val statusBarResourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        val systemNavigationBarResourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")

        // getDimension(): dimen.xml에 정의한 dp값을 기기에 맞게 px로 변환하여 반올림한 값을 int로 반환한다.
        val statusBarHeight = resources.getDimension(statusBarResourceId)
        val systemNavigationBarHeight = resources.getDimension(systemNavigationBarResourceId)
        // 상단 상태바, 시스템 네비게이션 바 제외한 화면 높이
//        GlobalValue.screenHeightWithoutStatusBar = screenHeight.toFloat()
        TOTAL_SCREEN_HEIGHT = size.height / density
        SYSTEM_STATUS_BAR_HEIGHT = statusBarHeight / density
        SYSTEM_NAVIGATION_BAR_HEIGHT = systemNavigationBarHeight / density
        SCREEN_HEIGHT_WITHOUT_SYSTEM_BAR = size.height - statusBarHeight - systemNavigationBarHeight
        SCREEN_WIDTH = size.width.toFloat() / density
        BOTTOM_NAVIGATION_BAR_HEIGHT = 60f
        TOP_BAR_HEIGHT = 42f
        CALENDAR_VIEW_HEIGHT = SCREEN_HEIGHT_WITHOUT_SYSTEM_BAR * 26 / 75
        DAILY_BRIEF_SCHEDULE_VIEW_HEIGHT = SCREEN_HEIGHT_WITHOUT_SYSTEM_BAR * 39 / 75
        Log.e("ScreenValue", "totalScreenHeight: $TOTAL_SCREEN_HEIGHT\n" +
                "statusBarHeight: $statusBarHeight ${statusBarHeight / density}\n" +
                "systemNavigationBarHeight: $systemNavigationBarHeight ${systemNavigationBarHeight / density}\n" +
                "screenHeightWithoutStatusBar: $SCREEN_HEIGHT_WITHOUT_SYSTEM_BAR ${SCREEN_HEIGHT_WITHOUT_SYSTEM_BAR / density}\n" +
                "screenWidth: $SCREEN_WIDTH ${SCREEN_WIDTH / density}\n" +
                "bottomNavBarHeight: $BOTTOM_NAVIGATION_BAR_HEIGHT\n" +
                "topBarHeight: $TOP_BAR_HEIGHT ${TOP_BAR_HEIGHT / density}\n" +
                "calendarViewHeight: $CALENDAR_VIEW_HEIGHT ${CALENDAR_VIEW_HEIGHT / density}\n" +
                "dailyScheduleViewHeight: $DAILY_BRIEF_SCHEDULE_VIEW_HEIGHT ${DAILY_BRIEF_SCHEDULE_VIEW_HEIGHT / density}"
        )
    }





}

