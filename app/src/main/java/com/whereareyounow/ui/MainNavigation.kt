package com.whereareyounow.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whereareyounow.GlobalViewModel
import com.whereareyounow.data.Constants
import com.whereareyounow.ui.home.HomeScreen
import com.whereareyounow.ui.home.Tablayout
import com.whereareyounow.ui.home.friends.addfriend.AddFriendScreen
import com.whereareyounow.ui.home.schedule.detailschedule.DetailScheduleScreen
import com.whereareyounow.ui.home.schedule.newschedule.NewScheduleScreen
import com.whereareyounow.ui.signin.AgreeScreen
import com.whereareyounow.ui.signin.LoginScreen
import com.whereareyounow.ui.signup.SignUpScreen
import com.whereareyounow.ui.signin.SuccessScreen
import com.whereareyounow.ui.signin.SuccessScreenPw
import com.whereareyounow.ui.splash.SplashScreen
import com.whereareyounow.ui.start.StartScreen
import test

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController(),
    viewModel: GlobalViewModel = hiltViewModel()
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        navController = navController,
//        startDestination = Constants.ROUTE_MAIN_SPLASH
        startDestination = Constants.ROUTE_MAIN_SIGNUP
    ) {
        // 스플래시 화면
        composable(
            route = Constants.ROUTE_MAIN_SPLASH
        ) {
            SplashScreen(
                moveToStartScreen = {
                    navController.popBackStack()
                    navController.navigate(Constants.ROUTE_MAIN_START)
//                    navController.navigate(Constants.ROUTE_MAIN_SIGNIN)
//                    navController.navigate(Constants.ROUTE_MAIN_SIGNUP)
                },
                moveToMainScreen = {
                    navController.popBackStack()
                    navController.navigate(Constants.ROUTE_MAIN_HOME)
                },
            )
        }

        // 회원가입 화면
        composable(
            route = Constants.ROUTE_MAIN_SIGNUP
        ) {
            SignUpScreen(
                moveToBackScreen = { navController.popBackStack() },
            )
        }

        // 첫화면
        composable(route=Constants.ROUTE_MAIN_START){
            StartScreen(
                moveToSignUpScreen = { navController.navigate(Constants.ROUTE_MAIN_SIGNUP) },
                moveToSignInScreen = { navController.navigate(Constants.ROUTE_MAIN_SIGNIN) }
            )
        }

        // 로그인 화면
        composable(route=Constants.ROUTE_MAIN_SIGNIN){
            LoginScreen(
                moveToStartScreen = { navController.navigate(Constants.ROUTE_MAIN_START) },
                moveToMainHomeScreen = {
                    navController.popBackStack(Constants.ROUTE_MAIN_START, true)
                    navController.navigate(Constants.ROUTE_MAIN_HOME)
                },
                moveToFindIdScreen = { navController.navigate(Constants.ROUTE_MAIN_FINDID) },
                moveToFindPWScreen = { navController.navigate(Constants.ROUTE_MAIN_FINDPW) },
                moveToBackScreen = { navController.popBackStack() }
            )
        }

        // 홈 화면
        composable(
            route = Constants.ROUTE_MAIN_HOME
        ) {
            HomeScreen(
                moveToAddScheduleScreen = { navController.navigate(Constants.ROUTE_NEW_SCHEDULE) },
                moveToDetailScreen = { navController.navigate(Constants.ROUTE_DETAIL_SCHEDULE + "/$it") },
                moveToAddFriendScreen = { navController.navigate(Constants.ROUTE_ADD_FRIEND) },
                moveToAddGroupScreen = {  },
                moveToStartScreen = {
                    navController.popBackStack()
                    navController.navigate(Constants.ROUTE_MAIN_START)
                }
            )
        }

        // 일정 추가 화면
        composable(
            route = Constants.ROUTE_NEW_SCHEDULE
        ) {
            NewScheduleScreen(
                moveToCalendarScreen = { navController.navigate(Constants.ROUTE_MAIN_HOME) },
            )
        }

        // 상세 일정 정보 화면
        composable(
            route = Constants.ROUTE_DETAIL_SCHEDULE + "/{scheduleId}"
        ) {
            DetailScheduleScreen(it.arguments?.getString("scheduleId"))
        }

        // 친구 추가 화면
        composable(
            route = Constants.ROUTE_ADD_FRIEND
        ) {
            AddFriendScreen()
        }

        // 아이디 찾기(왼쪽)
        composable(route=Constants.ROUTE_MAIN_FINDID){
            Tablayout(
                moveToSignInScreen = {
//                    Log.e("moveToSignInScreen", "moveToSignInScreen")
                    navController.popBackStack()
                    navController.navigate(Constants.ROUTE_MAIN_SIGNIN)
                },
                num = 0,
                navController = navController
            )
        }
        // 아이디 찾기(오른쪽)
        composable(route=Constants.ROUTE_MAIN_FINDIDSUCCESS){
            Tablayout(
                moveToSignInScreen = { navController.navigate(Constants.ROUTE_MAIN_SIGNIN) },
                num = 2,
                navController = navController
            )
        }

        // 비밀번호 재설정(왼쪽)
        composable(route=Constants.ROUTE_MAIN_FINDPW){
            Tablayout(
                moveToSignInScreen = { navController.navigate(Constants.ROUTE_MAIN_SIGNIN) },
                num = 1,
                navController = navController
            )
        }

        // 비밀번호 재설정(오른쪽)
        composable(route=Constants.ROUTE_MAIN_FINDPWSUCCESS){
            Tablayout(
                moveToSignInScreen = { navController.navigate(Constants.ROUTE_MAIN_SIGNIN) },
                num = 3,
                navController = navController
            )
        }


        // 약관동의
        composable(route=Constants.ROUTE_MAIN_AGREE){
            AgreeScreen(navController = navController)
        }

        // 회원가입 완료
        composable(route=Constants.ROUTE_MAIN_SUCCESS){
            SuccessScreen(navController = navController)
        }

        // 비밀번호 변경 완료
        composable(route=Constants.ROUTE_MAIN_SUCCESSPW){
            SuccessScreenPw(navController = navController)
        }


        composable(route=Constants.ROUTE_TEST){
            test()
        }
    }
}