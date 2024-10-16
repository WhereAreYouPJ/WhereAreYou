package com.whereareyounow.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whereareyounow.data.detailschedule.MemberInfo
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.data.globalvalue.ROUTE_DETAIL_PROFILE
import com.whereareyounow.data.globalvalue.ROUTE_DETAIL_SCHEDULE_MAP
import com.whereareyounow.data.globalvalue.ROUTE_MODIFY_INFO
import com.whereareyounow.data.globalvalue.ROUTE_MY_INFO
import com.whereareyounow.ui.main.friend.DetailProfileScreen
import com.whereareyounow.ui.main.mypage.InfoModificationScreen
import com.whereareyounow.ui.main.mypage.announcement.AdminImageScreen
import com.whereareyounow.ui.main.mypage.announcement.AnnouncementScreen
import com.whereareyounow.ui.main.mypage.ask.AskScreen
import com.whereareyounow.ui.main.mypage.byebye.ByeScreen1
import com.whereareyounow.ui.main.mypage.byebye.ByeScreen2
import com.whereareyounow.ui.main.mypage.byebye.ByeScreen3
import com.whereareyounow.ui.main.mypage.byebye.ByeScreen4
import com.whereareyounow.ui.main.mypage.byebye.ByeScreen5
import com.whereareyounow.ui.main.mypage.location.EditLocationFavoriteScreen
import com.whereareyounow.ui.main.mypage.location.LocationFavoriteScreen
import com.whereareyounow.ui.main.mypage.myinfo.EditMyInfoScreen
import com.whereareyounow.ui.main.mypage.myinfo.MyInfoScreen
import com.whereareyounow.ui.main.schedule.detailschedule.DetailScheduleMapScreen
import com.whereareyounow.ui.navigation.accountDuplicateScreenRoute
import com.whereareyounow.ui.navigation.addFeedScreenRoute
import com.whereareyounow.ui.navigation.detailScheduleMapScreenRoute
import com.whereareyounow.ui.navigation.detailScheduleScreenRoute
import com.whereareyounow.ui.navigation.developerScreenRoute
import com.whereareyounow.ui.navigation.feedBookMarkRoute
import com.whereareyounow.ui.navigation.feedStoreRoute
import com.whereareyounow.ui.navigation.findAccountEmailVerificationScreenRoute
import com.whereareyounow.ui.navigation.findAccountResultScreenRoute
import com.whereareyounow.ui.navigation.findPasswordScreenRoute
import com.whereareyounow.ui.navigation.friend.searchAndAddFriendScreen
import com.whereareyounow.ui.navigation.friendsListScreenRoute
import com.whereareyounow.ui.navigation.kakaoSignUpScreenRoute
import com.whereareyounow.ui.navigation.locationPolicyDetailsScreenRoute
import com.whereareyounow.ui.navigation.mainScreenRoute
import com.whereareyounow.ui.navigation.modifyScheduleScreenRoute
import com.whereareyounow.ui.navigation.newScheduleScreenRoute
import com.whereareyounow.ui.navigation.notificationScreenRoute
import com.whereareyounow.ui.navigation.passwordResetSuccessScreenRoute
import com.whereareyounow.ui.navigation.passwordResettingScreenRoute
import com.whereareyounow.ui.navigation.policyAgreeScreenRoute
import com.whereareyounow.ui.navigation.privacyPolicyDetailsScreenRoute
import com.whereareyounow.ui.navigation.searchLocationMapScreenRoute
import com.whereareyounow.ui.navigation.searchLocationScreenRoute
import com.whereareyounow.ui.navigation.signInMethodSelectionScreenRoute
import com.whereareyounow.ui.navigation.signInWithAccountScreenRoute
import com.whereareyounow.ui.navigation.signUpScreenRoute
import com.whereareyounow.ui.navigation.signUpSuccessScreenRoute
import com.whereareyounow.ui.navigation.splashScreenRoute
import com.whereareyounow.ui.navigation.termsOfServiceDetailsScreenRoute

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = ROUTE.Splash,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {
        // 개발자 화면
        developerScreenRoute(navController)

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

        // 계정 찾기 결과 화면
        findAccountResultScreenRoute(navController)

        // 비밀번호 찾기 이메일 인증 화면
        findPasswordScreenRoute(navController)

        // 비밀번호 재설정 화면
        passwordResettingScreenRoute(navController)

        // 비밀번호 찾기 성공 화면
        passwordResetSuccessScreenRoute(navController)

        // 메인 화면
        mainScreenRoute(navController)

        // 알림 화면
        notificationScreenRoute(navController)

        // 일정 추가 화면
        newScheduleScreenRoute(navController)

        // 일정 상세 화면
        detailScheduleScreenRoute(navController)

        // 일정 수정 화면
        modifyScheduleScreenRoute(navController)

        // 장소 검색 화면
        searchLocationScreenRoute(navController)

        // 장소 검색 - 지도 화면
        searchLocationMapScreenRoute(navController)

        // 친구 선택 화면
        friendsListScreenRoute(navController)

        // 상세 일정 정보 지도 화면
        detailScheduleMapScreenRoute(navController)

        // 친구 추가 화면
        searchAndAddFriendScreen(navController)

        // 피드 추가 화면
        addFeedScreenRoute(navController)

        // 회원 정보 수정 화면
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
        composable(route = ROUTE_MY_INFO) {
            MyInfoScreen(
                moveToMyPageScreen = { navController.popBackStack() },
                moveToEditMyInfoScreen = { navController.navigate(ROUTE.EditMyInfo) }
            )
        }

        // 내 정보 수정 화면
        composable<ROUTE.EditMyInfo> {
            EditMyInfoScreen(
                moveToMyInfoScreen = { navController.popBackStack() },
                moveToBackScreen = { navController.popBackStack() }
            )
        }

        // 공지사항 화면
        composable<ROUTE.Announcement> {
            AnnouncementScreen(
                moveToBackScreen = { navController.popBackStack() },
                moveToAdminImageScreen = { navController.navigate(ROUTE.AdminImageScreen) }
            )
        }

        // 1 : 1 이용문의
        composable<ROUTE.Ask> {
            AskScreen(
                moveToBackScreen = { navController.popBackStack() }
            )
        }

        // 회원탈퇴1
        composable<ROUTE.Bye1> {
            ByeScreen1(
                moveToBackScreen = { navController.popBackStack() },
                moveToByeScreen2 = { navController.navigate(ROUTE.Bye2) }
            )
        }
        // 회원탈퇴2
        composable<ROUTE.Bye2> {
            ByeScreen2(
                moveToBackScreen = { navController.popBackStack() },
                moveToByeScreen3 = { navController.navigate(ROUTE.Bye3) }
            )
        }
        // 회원탈퇴3
        composable<ROUTE.Bye3> {
            ByeScreen3(
                moveToBackScreen = { navController.popBackStack() },
                moveToByeScreen4 = { navController.navigate(ROUTE.Bye4) }
            )
        }
        // 회원탈퇴4
        composable<ROUTE.Bye4> {
            ByeScreen4(
                moveToBackScreen = { navController.popBackStack() },
                moveToByeScreen5 = { navController.navigate(ROUTE.Bye5) }
            )
        }
        // 회원탈퇴5
        composable<ROUTE.Bye5> {
            ByeScreen5(
                moveToBackScreen = { navController.popBackStack() },
                moveToLoginScreen = { }
            )
        }
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