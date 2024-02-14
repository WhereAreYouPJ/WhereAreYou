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
import com.whereareyounow.data.ROUTE_FIND_ID
import com.whereareyounow.data.ROUTE_FIND_PASSWORD
import com.whereareyounow.data.ROUTE_HOME
import com.whereareyounow.data.ROUTE_MODIFY_INFO
import com.whereareyounow.data.ROUTE_NEW_SCHEDULE
import com.whereareyounow.data.ROUTE_POLICY_AGREE
import com.whereareyounow.data.ROUTE_PRIVACY_POLICY
import com.whereareyounow.data.ROUTE_SIGN_IN
import com.whereareyounow.data.ROUTE_SIGN_UP
import com.whereareyounow.data.ROUTE_SIGN_UP_SUCCESS
import com.whereareyounow.data.ROUTE_SPLASH
import com.whereareyounow.data.ROUTE_TERMS_OF_SERVICE
import com.whereareyounow.ui.findid.FindIdContent
import com.whereareyounow.ui.findpw.FindPasswordContent
import com.whereareyounow.ui.home.HomeContent
import com.whereareyounow.ui.home.friend.addfriend.AddFriendScreen
import com.whereareyounow.ui.home.mypage.InfoModificationScreen
import com.whereareyounow.ui.home.schedule.detailschedule.DetailScheduleContent
import com.whereareyounow.ui.home.schedule.newschedule.NewScheduleContent
import com.whereareyounow.ui.signin.SignInScreen
import com.whereareyounow.ui.signup.PolicyAgreeScreen
import com.whereareyounow.ui.signup.PrivacyPolicyDetailsScreen
import com.whereareyounow.ui.signup.SignUpScreen
import com.whereareyounow.ui.signup.SignUpSuccessScreen
import com.whereareyounow.ui.signup.TermsOfServiceDetailsScreen
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
                    navController.navigate(ROUTE_SIGN_IN) {
                        popUpTo(ROUTE_SPLASH) { inclusive = true }
                    }
                },
                moveToMainScreen = {
                    navController.navigate(ROUTE_HOME) {
                        popUpTo(ROUTE_SPLASH) { inclusive = true }
                    }
                },
            )
        }

        // 약관 동의 화면
        composable(route = ROUTE_POLICY_AGREE) {
            PolicyAgreeScreen(
                moveToBackScreen = { navController.popBackStack() },
                moveToSignUpScreen = {
                    navController.navigate(ROUTE_SIGN_UP) {
                        popUpTo(ROUTE_SIGN_IN)
                    }
                },
                moveToTermsOfServiceDetailsScreen = { navController.navigate(ROUTE_TERMS_OF_SERVICE) },
                moveToPrivacyPolicyDetailsScreen = { navController.navigate(ROUTE_TERMS_OF_SERVICE) }
            )
        }

        // 서비스 이용약관 화면
        composable(route = ROUTE_TERMS_OF_SERVICE) {
            TermsOfServiceDetailsScreen(
                moveToPolicyAgreeScreen = { navController.popBackStack() }
            )
        }

        // 개인정보 처리방침 화면
        composable(route = ROUTE_PRIVACY_POLICY) {
            PrivacyPolicyDetailsScreen(
                moveToPolicyAgreeScreen = { navController.popBackStack() }
            )
        }

        // 회원가입 화면
        composable(route = ROUTE_SIGN_UP) {
            SignUpScreen(
                moveToBackScreen = { navController.popBackStack(ROUTE_SIGN_IN, false) },
                moveToSignUpSuccessScreen = {
                    navController.navigate(ROUTE_SIGN_UP_SUCCESS) {
                        popUpTo(ROUTE_SIGN_IN)
                    }
                }
            )
        }

        // 회원가입 성공 화면
        composable(route = ROUTE_SIGN_UP_SUCCESS) {
            SignUpSuccessScreen(
                moveToBackScreen = { navController.popBackStack(ROUTE_SIGN_IN, false) }
            )
        }

        // 로그인 화면
        composable(route = ROUTE_SIGN_IN){
            SignInScreen(
                moveToMainHomeScreen = {
                    navController.navigate(ROUTE_HOME) {
                        popUpTo(ROUTE_SIGN_IN) { inclusive = true }
                    }
                },
                moveToFindIdScreen = { navController.navigate(ROUTE_FIND_ID) },
                moveToFindPasswordScreen = { navController.navigate(ROUTE_FIND_PASSWORD) },
                moveToSignUpScreen = { navController.navigate(ROUTE_POLICY_AGREE) }
            )
        }

        // 아이디 찾기 화면
        composable(route = ROUTE_FIND_ID) {
            FindIdContent(
                moveToSignInScreen = { navController.popBackStack(ROUTE_SIGN_IN, false) }
            )
        }

        // 비밀번호 찾기 화면
        composable(route = ROUTE_FIND_PASSWORD) {
            FindPasswordContent(
                moveToSignInScreen = { navController.popBackStack(ROUTE_SIGN_IN, false) }
            )
        }

        // 홈 화면
        composable(route =  ROUTE_HOME) {
            HomeContent(
                moveToAddScheduleScreen = { navController.navigate("$ROUTE_NEW_SCHEDULE/$it") },
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
        composable(route =  ROUTE_NEW_SCHEDULE + "/{date}") {
            NewScheduleContent(
                date = it.arguments?.getString("date") ?: "",
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