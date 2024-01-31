package com.whereareyounow.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whereareyounow.data.ROUTE_ADD_FRIEND
import com.whereareyounow.data.ROUTE_DETAIL_SCHEDULE
import com.whereareyounow.data.ROUTE_MAIN_FINDID
import com.whereareyounow.data.ROUTE_MAIN_FINDPW
import com.whereareyounow.data.ROUTE_MAIN_HOME
import com.whereareyounow.data.ROUTE_MAIN_START
import com.whereareyounow.data.ROUTE_MODIFY_INFO
import com.whereareyounow.data.ROUTE_NEW_SCHEDULE
import com.whereareyounow.data.ROUTE_SIGN_IN
import com.whereareyounow.data.ROUTE_SIGN_UP
import com.whereareyounow.data.ROUTE_SPLASH
import com.whereareyounow.ui.findid.FindIdContent
import com.whereareyounow.ui.findpw.FindPasswordContent
import com.whereareyounow.ui.home.HomeContent
import com.whereareyounow.ui.home.friend.addfriend.AddFriendScreen
import com.whereareyounow.ui.home.mypage.InfoModificationScreen
import com.whereareyounow.ui.home.schedule.detailschedule.DetailScheduleContent
import com.whereareyounow.ui.home.schedule.newschedule.NewScheduleContent
import com.whereareyounow.ui.signin.SignInScreen
import com.whereareyounow.ui.signup.SignUpContent
import com.whereareyounow.ui.splash.SplashScreen

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        navController = navController,
        startDestination = ROUTE_SPLASH
    ) {
        // 스플래시 화면
        composable(route = ROUTE_SPLASH) {
            SplashScreen(
                moveToSignInScreen = {
                    navController.popBackStack(ROUTE_SPLASH, true)
                    navController.navigate(ROUTE_SIGN_IN)
                },
                moveToMainScreen = {
                    navController.popBackStack(ROUTE_SPLASH, true)
                    navController.navigate(ROUTE_MAIN_HOME)
                },
            )
        }

        // 회원가입 화면
        composable(route = ROUTE_SIGN_UP) {
            SignUpContent(
                moveToBackScreen = { navController.popBackStack(ROUTE_SIGN_IN, false) }
            )
        }

        // 로그인 화면
        composable(route = ROUTE_SIGN_IN){
            SignInScreen(
                moveToMainHomeScreen = {
                    navController.popBackStack(ROUTE_SIGN_IN, true)
                    navController.navigate(ROUTE_MAIN_HOME)
                },
                moveToFindIdScreen = { navController.navigate(ROUTE_MAIN_FINDID) },
                moveToFindPasswordScreen = { navController.navigate(ROUTE_MAIN_FINDPW) },
                moveToSignUpScreen = { navController.navigate(ROUTE_SIGN_UP) }
            )
        }

        // 아이디 찾기 화면
        composable(route = ROUTE_MAIN_FINDID) {
            FindIdContent(
                moveToSignInScreen = { navController.popBackStack(ROUTE_SIGN_IN, false) }
            )
        }

        // 비밀번호 찾기 화면
        composable(route = ROUTE_MAIN_FINDPW) {
            FindPasswordContent(
                moveToSignInScreen = { navController.popBackStack(ROUTE_SIGN_IN, false) }
            )
        }

        // 홈 화면
        composable(route =  ROUTE_MAIN_HOME) {
            HomeContent(
                moveToAddScheduleScreen = { navController.navigate(ROUTE_NEW_SCHEDULE) },
                moveToDetailScreen = { navController.navigate("$ROUTE_DETAIL_SCHEDULE/$it") },
                moveToAddFriendScreen = { navController.navigate(ROUTE_ADD_FRIEND) },
                moveToAddGroupScreen = {},
                moveToSignInScreen = {
                    navController.popBackStack()
                    navController.navigate(ROUTE_SIGN_IN)
                },
                moveToModifyInfoScreen = { navController.navigate(ROUTE_MODIFY_INFO) }
            )
        }

        // 일정 추가 화면
        composable(route =  ROUTE_NEW_SCHEDULE) {
            NewScheduleContent(
                moveToBackScreen = { navController.popBackStack() },
            )
        }

        // 상세 일정 정보 화면
        composable(route =  ROUTE_DETAIL_SCHEDULE + "/{scheduleId}") {
            DetailScheduleContent(
                scheduleId = it.arguments?.getString("scheduleId") ?: "",
                moveToBackScreen = { navController.popBackStack() },
            )
        }

        // 친구 추가 화면
        composable(route =  ROUTE_ADD_FRIEND) {
            AddFriendScreen(
                moveToBackScreen = { navController.popBackStack() }
            )
        }

        // 회원 정보 수정 화면
        composable(route =  ROUTE_MODIFY_INFO) {
            InfoModificationScreen(
                moveToBackScreen = {
                    navController.popBackStack()
                }
            )
        }
    }
}