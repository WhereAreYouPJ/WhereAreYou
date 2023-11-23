package com.whereareyou.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whereareyou.GlobalViewModel
import com.whereareyou.data.Constants
import com.whereareyou.ui.home.HomeScreen
import com.whereareyou.ui.home.Tablayout
import com.whereareyou.ui.home.friends.addfriend.AddFriendScreen
import com.whereareyou.ui.home.schedule.detailschedule.DetailScheduleScreen
import com.whereareyou.ui.home.schedule.newschedule.NewScheduleScreen
import com.whereareyou.ui.signin.AgreeScreen
import com.whereareyou.ui.signin.LoginScreen
import com.whereareyou.ui.signin.SignUpScreen
import com.whereareyou.ui.signin.SuccessScreen
import com.whereareyou.ui.signin.SuccessScreenPw
import com.whereareyou.ui.splash.SplashScreen
import com.whereareyou.ui.start.StartScreen
import test

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController(),
    viewModel: GlobalViewModel = hiltViewModel()
) {
    Log.e("MainNav", "MainNav")
    NavHost(
        modifier = Modifier
            .fillMaxSize(),
        navController = navController,
        startDestination = Constants.ROUTE_MAIN_SPLASH
    ) {
        // 스플래시 화면
        composable(
            route = Constants.ROUTE_MAIN_SPLASH
        ) {
            SplashScreen(
                checkIsSignedIn = { viewModel.checkIsSignedIn() },
                moveToSignInScreen = {
                    navController.popBackStack()
//                    navController.navigate(Constants.ROUTE_MAIN_SIGNUP)
                    navController.navigate(Constants.ROUTE_MAIN_START)
                },
                moveToMainScreen = {
                    navController.popBackStack()
//                    navController.navigate(Constants.ROUTE_MAIN_FINDID)
                    navController.navigate(Constants.ROUTE_MAIN_START)
                }
            )
        }

        composable(
            route = Constants.ROUTE_MAIN_INTRO
        ) {

        }

        // 홈 화면
        composable(
            route = Constants.ROUTE_MAIN_HOME
        ) {
            HomeScreen(
                moveToAddScheduleScreen = { navController.navigate(Constants.ROUTE_NEW_SCHEDULE) },
                moveToDetailScreen = { navController.navigate(Constants.ROUTE_DETAIL_SCHEDULE + "/$it") },
                moveToAddFriendScreen = { navController.navigate(Constants.ROUTE_ADD_FRIEND) },
                moveToAddGroupScreen = {  }
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

        // 회원가입 화면
        composable(
            route = Constants.ROUTE_MAIN_SIGNUP
        ) {
            SignUpScreen(navController = navController)
        }

        // 로그인 화면
        composable(route=Constants.ROUTE_MAIN_LOGIN){
            LoginScreen(
                moveToStartScreen = { navController.navigate(Constants.ROUTE_MAIN_START) },
                moveToMainHomeScreen = { navController.navigate(Constants.ROUTE_MAIN_HOME) },
                moveToFindIdScreen = { navController.navigate(Constants.ROUTE_MAIN_FINDID) },
                moveToFindPWScreen = { navController.navigate(Constants.ROUTE_MAIN_FINDPW) },
                moveToBackScreen = { navController.popBackStack() }
            )
        }

        // 첫화면
        composable(route=Constants.ROUTE_MAIN_START){
            for (backstack in navController.currentBackStack.value) {
                Log.e("route", "${backstack.destination.route}")
            }
            StartScreen(
                moveToSignUpScreen = { navController.navigate(Constants.ROUTE_MAIN_SIGNUP) },
                moveToSignInScreen = { navController.navigate(Constants.ROUTE_MAIN_LOGIN) }
            )
        }

        // 아이디 찾기(왼쪽)
        composable(route=Constants.ROUTE_MAIN_FINDID){
            Tablayout(
                moveToSignInScreen = {
//                    Log.e("moveToSignInScreen", "moveToSignInScreen")
                    navController.popBackStack()
                    navController.navigate(Constants.ROUTE_MAIN_LOGIN)
                },
                num = 0,
                navController = navController
            )
        }
        // 아이디 찾기(오른쪽)
        composable(route=Constants.ROUTE_MAIN_FINDIDSUCCESS){
            Tablayout(
                moveToSignInScreen = { navController.navigate(Constants.ROUTE_MAIN_LOGIN) },
                num = 2,
                navController = navController
            )
        }

        // 비밀번호 재설정(왼쪽)
        composable(route=Constants.ROUTE_MAIN_FINDPW){
            Tablayout(
                moveToSignInScreen = { navController.navigate(Constants.ROUTE_MAIN_LOGIN) },
                num = 1,
                navController = navController
            )
        }

        // 비밀번호 재설정(오른쪽)
        composable(route=Constants.ROUTE_MAIN_FINDPWSUCCESS){
            Tablayout(
                moveToSignInScreen = { navController.navigate(Constants.ROUTE_MAIN_LOGIN) },
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