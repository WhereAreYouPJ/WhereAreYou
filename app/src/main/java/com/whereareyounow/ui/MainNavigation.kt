package com.whereareyounow.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whereareyounow.data.detailschedule.MemberInfo
import com.whereareyounow.data.findpw.ResultState
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.data.globalvalue.ROUTE_DETAIL_PROFILE
import com.whereareyounow.data.globalvalue.ROUTE_DETAIL_SCHEDULE_MAP
import com.whereareyounow.data.globalvalue.ROUTE_FIND_ACCOUNT
import com.whereareyounow.data.globalvalue.ROUTE_FIND_ID_RESULT
import com.whereareyounow.data.globalvalue.ROUTE_FIND_PASSWORD_RESULT
import com.whereareyounow.data.globalvalue.ROUTE_FIND_PASSWORD_SUCCESS
import com.whereareyounow.data.globalvalue.ROUTE_MODIFY_INFO
import com.whereareyounow.data.globalvalue.ROUTE_MY_INFO
import com.whereareyounow.data.globalvalue.ROUTE_RESET_PASSWORD
import com.whereareyounow.data.globalvalue.ROUTE_SIGN_IN_WITH_ACCOUNT
import com.whereareyounow.ui.findaccount.findid.FindIdResultScreen
import com.whereareyounow.ui.findaccount.findpw.FindPasswordScreen
import com.whereareyounow.ui.findaccount.findpw.PasswordResetSuccessScreen
import com.whereareyounow.ui.findaccount.findpw.PasswordResettingScreen
import com.whereareyounow.ui.main.friend.DetailProfileScreen
import com.whereareyounow.ui.main.mypage.InfoModificationScreen
import com.whereareyounow.ui.main.mypage.announcement.AdminImageScreen
import com.whereareyounow.ui.main.mypage.announcement.AnnouncementScreen
import com.whereareyounow.ui.main.mypage.ask.AskScreen
import com.whereareyounow.ui.main.mypage.withdrawl.WithDrawalScreen2
import com.whereareyounow.ui.main.mypage.withdrawl.WithDrawalScreen3
import com.whereareyounow.ui.main.mypage.withdrawl.WithDrawalScreen4
import com.whereareyounow.ui.main.mypage.withdrawl.WithDrawalScreen5
import com.whereareyounow.ui.main.mypage.location.EditLocationFavoriteScreen
import com.whereareyounow.ui.main.mypage.location.LocationFavoriteScreen
import com.whereareyounow.ui.main.mypage.myinfo.EditMyInfoScreen
import com.whereareyounow.ui.main.mypage.myinfo.MyInfoScreen
import com.whereareyounow.ui.main.schedule.detailschedule.DetailScheduleMapScreen
import com.whereareyounow.ui.navigation.accountDuplicateScreenRoute
import com.whereareyounow.ui.navigation.detailScheduleScreenRoute
import com.whereareyounow.ui.navigation.feedBookMarkRoute
import com.whereareyounow.ui.navigation.feedStoreRoute
import com.whereareyounow.ui.navigation.findAccountEmailVerificationScreenRoute
import com.whereareyounow.ui.navigation.friend.searchAndAddFriendScreen
import com.whereareyounow.ui.navigation.friendsListScreenRoute
import com.whereareyounow.ui.navigation.kakaoSignUpScreenRoute
import com.whereareyounow.ui.navigation.locationPolicyDetailsScreenRoute
import com.whereareyounow.ui.navigation.mainScreenRoute
import com.whereareyounow.ui.navigation.mypage.announcement.announcementRoute
import com.whereareyounow.ui.navigation.mypage.ask.askRoute
import com.whereareyounow.ui.navigation.mypage.myinfo.editMyInfo
import com.whereareyounow.ui.navigation.mypage.myinfo.myInfo
import com.whereareyounow.ui.navigation.newScheduleScreenRoute
import com.whereareyounow.ui.navigation.policyAgreeScreenRoute
import com.whereareyounow.ui.navigation.privacyPolicyDetailsScreenRoute
import com.whereareyounow.ui.navigation.scheduleModificationScreenRoute
import com.whereareyounow.ui.navigation.searchLocationMapScreenRoute
import com.whereareyounow.ui.navigation.searchLocationScreenRoute
import com.whereareyounow.ui.navigation.signInMethodSelectionScreenRoute
import com.whereareyounow.ui.navigation.signInWithAccountScreenRoute
import com.whereareyounow.ui.navigation.signUpScreenRoute
import com.whereareyounow.ui.navigation.signUpSuccessScreenRoute
import com.whereareyounow.ui.navigation.splashScreenRoute
import com.whereareyounow.ui.navigation.termsOfServiceDetailsScreenRoute
import com.whereareyounow.ui.navigation.mypage.withdrawl.withDrawlRoute1
import com.whereareyounow.ui.navigation.mypage.withdrawl.withDrawlRoute2
import com.whereareyounow.ui.navigation.mypage.withdrawl.withDrawlRoute3
import com.whereareyounow.ui.navigation.mypage.withdrawl.withDrawlRoute4
import com.whereareyounow.ui.navigation.mypage.withdrawl.withDrawlRoute5
import com.whereareyounow.util.navigate

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = ROUTE.Splash,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        // 스플래시
        splashScreenRoute(navController)

        // 로그인 방법 선택
        signInMethodSelectionScreenRoute(navController)

        // 약관 동의
        policyAgreeScreenRoute(navController)

        // 서비스 이용약관
        termsOfServiceDetailsScreenRoute(navController)

        // 개인정보 처리방침
        privacyPolicyDetailsScreenRoute(navController)

        // 위치기반 서비스 이용약관
        locationPolicyDetailsScreenRoute(navController)

        // 일반 회원가입
        signUpScreenRoute(navController)

        // 카카오 회원가입
        kakaoSignUpScreenRoute(navController)

        // 회원가입 성공 화면
        signUpSuccessScreenRoute(navController)

        // 계정으로 로그인 화면
        signInWithAccountScreenRoute(navController)

        // 계정 연동 화면
        accountDuplicateScreenRoute(navController)

        // 계정 찾기 이메일 인증 화면
        findAccountEmailVerificationScreenRoute(navController)

        // 아이디 찾기 결과 화면
        composable(route = ROUTE_FIND_ID_RESULT) {
            FindIdResultScreen(
                searchedUserId = it.arguments?.getString("searchedUserId") ?: "",
                moveToResetPasswordScreen = { userId ->
                    val bundle = bundleOf(
                        "userId" to userId
                    )
                    navController.popBackStack(ROUTE_FIND_ACCOUNT, true)
                    navController.navigate(ROUTE_RESET_PASSWORD, bundle)
                },
                moveToSignInScreen = {
                    navController.popBackStack(
                        ROUTE_SIGN_IN_WITH_ACCOUNT,
                        false
                    )
                }
            )
        }

        // 비밀번호 찾기 화면
        composable(route = ROUTE_RESET_PASSWORD) {
            FindPasswordScreen(
                moveToSignInScreen = { navController.popBackStack() },
                moveToPasswordResettingScreen = { userId, resultState ->
                    val bundle = bundleOf(
                        "userId" to userId,
                        "resultState" to resultState.name
                    )
                    navController.popBackStack(ROUTE_SIGN_IN_WITH_ACCOUNT, false)
                    navController.navigate(ROUTE_FIND_PASSWORD_RESULT, bundle)
                },
            )
        }

        // 비밀번호 찾기 결과 화면
        composable(route = ROUTE_FIND_PASSWORD_RESULT) {
            PasswordResettingScreen(
                userId = it.arguments?.getString("userId") ?: "",
                resultState = ResultState.valueOf(it.arguments?.getString("resultState") ?: "OK"),
                moveToSignInScreen = {
                    navController.popBackStack(
                        ROUTE_SIGN_IN_WITH_ACCOUNT,
                        false
                    )
                },
                moveToPasswordResetSuccessScreen = {
                    navController.navigate(ROUTE_FIND_PASSWORD_SUCCESS) {
                        popUpTo(ROUTE_SIGN_IN_WITH_ACCOUNT) { inclusive = false }
                    }
                }
            )
        }

        // 비밀번호 찾기 성공 화면
        composable(route = ROUTE_FIND_PASSWORD_SUCCESS) {
            PasswordResetSuccessScreen(
                moveToSignInScreen = {
                    navController.popBackStack(
                        ROUTE_SIGN_IN_WITH_ACCOUNT,
                        false
                    )
                }
            )
        }

        // 메인 화면
        mainScreenRoute(navController)

        // 일정 추가 화면
        newScheduleScreenRoute(navController)

        // 일정 상세 화면
        detailScheduleScreenRoute(navController)

        // 일정 수정 화면
        scheduleModificationScreenRoute(navController)

        // 장소 검색 화면
        searchLocationScreenRoute(navController)

        // 장소 검색 - 지도 화면
        searchLocationMapScreenRoute(navController)

        // 친구 선택 화면
        friendsListScreenRoute(navController)

        // 상세 일정 정보 지도 화면
        composable(
            route = ROUTE_DETAIL_SCHEDULE_MAP
        ) {
            DetailScheduleMapScreen(
                scheduleId = it.arguments?.getString("scheduleId") ?: "",
                destinationLatitude = it.arguments?.getDouble("destinationLatitude") ?: 0.0,
                destinationLongitude = it.arguments?.getDouble("destinationLongitude") ?: 0.0,
                passedMemberInfosList = it.arguments?.getParcelableArrayList<MemberInfo>("memberInfosList")
                    ?: emptyList<MemberInfo>()
            )
        }

        /**
         * [ 친구 검색 및 추가 화면 ]
         */
        searchAndAddFriendScreen(navController)


        /**
         * [ 회원 정보 수정 화면 ]
         */
        composable(route = ROUTE_MODIFY_INFO) {
            InfoModificationScreen(
                moveToBackScreen = {
                    navController.popBackStack()
                }
            )
        }

        // 프로필 상세 화면
        composable(route = ROUTE_DETAIL_PROFILE) {
            DetailProfileScreen(
                friendImagrUrl = it.arguments?.getString("friendImagrUrl") ?: "0",
                friendName = it.arguments?.getString("friendName") ?: "0",
                moveToBackScreen = { navController.popBackStack() }
            )
        }

        // 내 정보 화면
        myInfo(navController)

        // 내 정보 수정 화면
        editMyInfo(navController)

        // 공지사항 화면
        announcementRoute(navController)

        // 1 : 1 이용문의
        askRoute(navController)

        // 회원탈퇴1
        withDrawlRoute1(navController)

        // 회원탈퇴2
        withDrawlRoute2(navController)

        // 회원탈퇴3
        withDrawlRoute3(navController)

        // 회원탈퇴4
        withDrawlRoute4(navController)

        // 회원탈퇴5
        withDrawlRoute5(navController)

        // 어드민 페이지 이미지 스크린
        composable<ROUTE.AdminImageScreen> {
            AdminImageScreen(
                moveToBackScreen = { navController.popBackStack() }
            )
        }
        composable<ROUTE.LocationFaborite> {
            LocationFavoriteScreen(
                moveToBackScreen = { navController.popBackStack() },
                moveToEditLocationFavorite = { navController.navigate(ROUTE.EditLocationFaborite) }
            )
        }
        // 위치 즐겨찾기 편집 화면
        composable<ROUTE.EditLocationFaborite> {
            EditLocationFavoriteScreen(
                moveToBackScreen = { navController.popBackStack() }
            )
        }

        // 피드 북마크
        feedBookMarkRoute(navController)

        // 피드 보관함
        feedStoreRoute(navController)
    }
}