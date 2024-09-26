package com.onmyway

import android.Manifest
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.test.rule.GrantPermissionRule
import com.onmyway.test.RecompositionTestComposable
import com.onmyway.test.TestViewModel
import com.onmyway.ui.theme.OnMyWayTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
//@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
//    val composeTestRule = createComposeRule()
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    // 이렇게 규칙을 선언해줌으로써 테스트 중 자동으로 권한이 부여된다.
    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.WAKE_LOCK,
        Manifest.permission.READ_MEDIA_IMAGES
    )

    @Before
    fun init() {
        hiltRule.inject()
    }

    private lateinit var testViewModel: TestViewModel

    @Test
    fun test1() {
        composeTestRule.activity.setContent {
            OnMyWayTheme {
                testViewModel = hiltViewModel()
                Column() {
                    RecompositionTestComposable(testViewModel)
                }
            }
        }
        composeTestRule.onNode(hasContentDescription("text1")).assert(hasText("1: 0"))
        Thread.sleep(1000)
        composeTestRule.onNodeWithContentDescription("button1").performClick()
        Thread.sleep(1000)
        composeTestRule.onNode(hasContentDescription("text1")).assert(hasText("1: 1"))
    }

    @Test
    fun test2() {
//        composeTestRule.activity.setContent {
//            val navController = rememberNavController()
//            WhereAreYouTheme {
//                MainNavigation(navController)
//            }
//        }
//        composeTestRule.waitUntil { navController. }
//        Log.e("", "${navController.currentDestination?.route}")
//        composeTestRule.waitUntil { navController.currentDestination?.route == "" }
        Thread.sleep(1000)
        composeTestRule.onNode(hasContentDescription("Splash Logo")).assertIsDisplayed()
//        composeTestRule.onNode(hasContentDescription("text1")).assert(hasText("1: 0"))
//        Thread.sleep(1000)
//        composeTestRule.onNodeWithContentDescription("button1").performClick()
//        Thread.sleep(1000)
//        composeTestRule.onNode(hasContentDescription("text1")).assert(hasText("1: 2"))
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun test3() = runTest {
        composeTestRule.waitUntilAtLeastOneExists(hasContentDescription("Sign In Screen"), 10000)
        composeTestRule.onNode(hasContentDescription("Id TextField")).performTextInput("user1")
        composeTestRule.onNode(hasContentDescription("Password TextField")).performTextInput("00")
        composeTestRule.onNode(hasContentDescription("Login Button")).performClick()
        composeTestRule.waitUntilAtLeastOneExists(hasContentDescription("Main Calendar Screen"), 10000)
    }


//    @Inject
//    lateinit var calendarViewModel: CalendarViewModel
//
//    @Test
//    fun test4() = runTest {
//        calendarViewModel.updateYear(2024)
//        calendarViewModel.updateMonth(3)
//        calendarViewModel.updateDate(15)
//        calendarViewModel.updateCurrentMonthCalendarInfo()
//        calendarViewModel.calendarScreenUIState.collect {
//            assert(it.selectedDate == 15)
//        }
//    }

    @Test
    fun loginTest() {
        composeTestRule.activity.setContent {
            OnMyWayTheme() {
                Column {

                }
            }
        }
    }

//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.whereareyou", appContext.packageName)
//    }
}