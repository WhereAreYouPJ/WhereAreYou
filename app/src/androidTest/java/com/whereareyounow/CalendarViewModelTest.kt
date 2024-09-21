package com.whereareyounow

import android.Manifest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.ViewModelProvider
import androidx.test.rule.GrantPermissionRule
import com.whereareyounow.ui.main.schedule.calendar.CalendarViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
//@RunWith(AndroidJUnit4::class)
class CalendarViewModelTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class,
        ExperimentalTestApi::class
    )
    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.waitUntilAtLeastOneExists(hasContentDescription("Sign In Screen"), 10000)
        composeTestRule.onNode(hasContentDescription("Id TextField")).performTextInput("user1")
        composeTestRule.onNode(hasContentDescription("Password TextField")).performTextInput("00")
        composeTestRule.onNode(hasContentDescription("Login Button")).performClick()
        composeTestRule.waitUntilAtLeastOneExists(hasContentDescription("Main Calendar Screen"), 10000)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test44() = runBlocking {
        val calendarViewModel: CalendarViewModel by lazy {
            ViewModelProvider(composeTestRule.activity)[CalendarViewModel::class.java]
        }
        calendarViewModel.updateYear(2024)
        calendarViewModel.updateMonth(3)
        calendarViewModel.updateDate(14)
        calendarViewModel.updateCurrentMonthCalendarInfo()
        delay(5000)
        assertEquals(1, calendarViewModel.uiState.value.selectedMonthCalendarInfoMap[14].scheduleCount)
    }
}