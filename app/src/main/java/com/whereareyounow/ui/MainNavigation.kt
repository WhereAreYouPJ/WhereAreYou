package com.whereareyounow.ui

import android.util.Log
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whereareyounow.data.detailschedule.DetailScheduleScreenUIState
import com.whereareyounow.data.detailschedule.MemberInfo
import com.whereareyounow.data.findpw.ResultState
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.data.globalvalue.ROUTE_ADD_FRIEND
import com.whereareyounow.data.globalvalue.ROUTE_DETAIL_PROFILE
import com.whereareyounow.data.globalvalue.ROUTE_DETAIL_SCHEDULE
import com.whereareyounow.data.globalvalue.ROUTE_DETAIL_SCHEDULE_MAP
import com.whereareyounow.data.globalvalue.ROUTE_FIND_ACCOUNT
import com.whereareyounow.data.globalvalue.ROUTE_FIND_ID_RESULT
import com.whereareyounow.data.globalvalue.ROUTE_FIND_PASSWORD_RESULT
import com.whereareyounow.data.globalvalue.ROUTE_FIND_PASSWORD_SUCCESS
import com.whereareyounow.data.globalvalue.ROUTE_MODIFY_INFO
import com.whereareyounow.data.globalvalue.ROUTE_MODIFY_SCHEDULE
import com.whereareyounow.data.globalvalue.ROUTE_MY_INFO
import com.whereareyounow.data.globalvalue.ROUTE_NEW_SCHEDULE
import com.whereareyounow.data.globalvalue.ROUTE_RESET_PASSWORD
import com.whereareyounow.data.globalvalue.ROUTE_SEARCH_LOCATION
import com.whereareyounow.data.globalvalue.ROUTE_SEARCH_LOCATION_MAP
import com.whereareyounow.data.globalvalue.ROUTE_SELECT_FRIENDS
import com.whereareyounow.data.globalvalue.ROUTE_SIGN_IN_WITH_ACCOUNT
import com.whereareyounow.ui.findaccount.findid.FindIdResultScreen
import com.whereareyounow.ui.findaccount.findpw.FindPasswordScreen
import com.whereareyounow.ui.findaccount.findpw.PasswordResetSuccessScreen
import com.whereareyounow.ui.findaccount.findpw.PasswordResettingScreen
import com.whereareyounow.ui.main.friend.DetailProfileScreen
import com.whereareyounow.ui.main.friend.addfriend.AddFriendScreen
import com.whereareyounow.ui.main.mypage.announcement.AnnouncementScreen
import com.whereareyounow.ui.main.mypage.ask.AskScreen
import com.whereareyounow.ui.main.mypage.InfoModificationScreen
import com.whereareyounow.ui.main.mypage.announcement.AdminImageScreen
import com.whereareyounow.ui.main.mypage.myinfo.MyInfoScreen
import com.whereareyounow.ui.main.mypage.byebye.ByeScreen1
import com.whereareyounow.ui.main.mypage.byebye.ByeScreen2
import com.whereareyounow.ui.main.mypage.byebye.ByeScreen3
import com.whereareyounow.ui.main.mypage.byebye.ByeScreen4
import com.whereareyounow.ui.main.mypage.byebye.ByeScreen5
import com.whereareyounow.ui.main.mypage.location.EditLocationFaboriteScreen
import com.whereareyounow.ui.main.mypage.location.LocationFaboriteScreen
import com.whereareyounow.ui.main.mypage.myinfo.EditMyInfoScreen
import com.whereareyounow.ui.main.schedule.detailschedule.DetailScheduleMapScreen
import com.whereareyounow.ui.main.schedule.detailschedule.DetailScheduleScreen
import com.whereareyounow.ui.main.schedule.modifyschedule.ModifyScheduleScreen
import com.whereareyounow.ui.main.schedule.newschedule.NewScheduleScreen
import com.whereareyounow.ui.main.schedule.scheduleedit.FriendsListScreen
import com.whereareyounow.ui.main.schedule.scheduleedit.SearchLocationMapScreen
import com.whereareyounow.ui.main.schedule.scheduleedit.SearchLocationScreen
import com.whereareyounow.ui.navigation.findAccountEmailVerificationScreenRoute
import com.whereareyounow.ui.navigation.locationPolicyDetailsScreenRoute
import com.whereareyounow.ui.navigation.mainScreenRoute
import com.whereareyounow.ui.navigation.policyAgreeScreenRoute
import com.whereareyounow.ui.navigation.privacyPolicyDetailsScreenRoute
import com.whereareyounow.ui.navigation.signInMethodSelectionScreen
import com.whereareyounow.ui.navigation.signInWithAccountScreenRoute
import com.whereareyounow.ui.navigation.signUpScreenRoute
import com.whereareyounow.ui.navigation.signUpSuccessScreenRoute
import com.whereareyounow.ui.navigation.splashScreen
import com.whereareyounow.ui.navigation.termsOfServiceDetailsScreenRoute
import com.whereareyounow.util.navigate

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = ROUTE.Main,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        // 스플래시
        splashScreen(navController)

        // 로그인 방법 선택
        signInMethodSelectionScreen(navController)

        // 약관 동의
        policyAgreeScreenRoute(navController)

        // 서비스 이용약관
        termsOfServiceDetailsScreenRoute(navController)

        // 개인정보 처리방침
        privacyPolicyDetailsScreenRoute(navController)

        // 위치기반 서비스 이용약관
        locationPolicyDetailsScreenRoute(navController)

        // 회원가입 화면
        signUpScreenRoute(navController)

        // 회원가입 성공 화면
        signUpSuccessScreenRoute(navController)

        // 계정으로 로그인 화면
        signInWithAccountScreenRoute(navController)

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
        composable(
            route = ROUTE_NEW_SCHEDULE
        ) {
//            val destinationName = it.savedStateHandle.getStateFlow("destinationName", "").collectAsState().value
//            val destinationRoadAddress = it.savedStateHandle.getStateFlow("destinationRoadAddress", "").collectAsState().value
//            val destinationLatitude = it.savedStateHandle.getStateFlow("destinationLatitude", 0.0).collectAsState().value
//            val destinationLongitude = it.savedStateHandle.getStateFlow("destinationLongitude", 0.0).collectAsState().value
//            val selectedFriendIdsList = it.savedStateHandle.getStateFlow<List<String>>("selectedFriendIdsList", emptyList()).collectAsState().value
//            val parentEntry = remember(it) {
//                navController.getBackStackEntry("")
//            }
            NewScheduleScreen(
                initialYear = it.arguments?.getInt("year") ?: 0,
                initialMonth = it.arguments?.getInt("month") ?: 0,
                initialDate = it.arguments?.getInt("date") ?: 0,
                moveToSearchLocationScreen = { navController.navigate(ROUTE_SEARCH_LOCATION) },
                moveToFriendsListScreen = { navController.navigate(ROUTE_SELECT_FRIENDS) },
                moveToBackScreen = { navController.popBackStack() },
            )
        }

        // 장소 검색 화면
        composable(
            route = ROUTE_SEARCH_LOCATION
        ) {
            val parentEntry = remember(it) {
//                navController.getBackStackEntry(ROUTE_NEW_SCHEDULE)
                navController.previousBackStackEntry!!
            }
            SearchLocationScreen(
//                updateDestinationInformation = { name, roadAddress, lat, lng ->
//                    navController.previousBackStackEntry?.savedStateHandle?.set("destinationName", name)
//                    navController.previousBackStackEntry?.savedStateHandle?.set("destinationRoadAddress", roadAddress)
//                    navController.previousBackStackEntry?.savedStateHandle?.set("destinationLatitude", lat)
//                    navController.previousBackStackEntry?.savedStateHandle?.set("destinationLongitude", lng)
//                },
                moveToBackScreen = { navController.popBackStack() },
                moveToMapScreen = { lat, lng ->
                    val bundle = bundleOf(
                        "lat" to lat,
                        "lng" to lng
                    )
                    navController.navigate(ROUTE_SEARCH_LOCATION_MAP, bundle)
                },
                scheduleEditViewModel = hiltViewModel(parentEntry)
            )
        }

        // 장소 검색 - 지도 화면
        composable(
            route = ROUTE_SEARCH_LOCATION_MAP
        ) {
            Log.e(
                "SearchLocationMapScreen",
                "${it.arguments?.getString("lat")?.toDouble()}, ${
                    it.arguments?.getString("lng")?.toDouble()
                }"
            )
            SearchLocationMapScreen(
                moveToBackScreen = { navController.popBackStack() },
                latitude = it.arguments?.getDouble("lat") ?: 0.0,
                longitude = it.arguments?.getDouble("lng") ?: 0.0
            )
        }

        // 친구 선택 화면
        composable(
            route = ROUTE_SELECT_FRIENDS
        ) {
            val parentEntry = remember(it) {
//                navController.getBackStackEntry(ROUTE_NEW_SCHEDULE)
                navController.previousBackStackEntry!!
            }
            FriendsListScreen(
                moveToBackScreen = { navController.popBackStack() },
                scheduleEditViewModel = hiltViewModel(parentEntry)
            )
        }

        // 상세 일정 정보 화면
        composable(route = ROUTE_DETAIL_SCHEDULE) {
            DetailScheduleScreen(
                scheduleId = it.arguments?.getString("scheduleId") ?: "",
                moveToDetailScheduleMapScreen = { scheduleId, destinationLatitude, destinationLongitude, memberInfosList ->
                    val bundle = bundleOf(
                        "scheduleId" to scheduleId,
                        "destinationLatitude" to destinationLatitude,
                        "destinationLongitude" to destinationLongitude
                    ).apply {
                        putParcelableArrayList("memberInfosList", ArrayList(memberInfosList))
                    }
                    navController.navigate(ROUTE_DETAIL_SCHEDULE_MAP, bundle)
                },
                moveToModifyScheduleScreen = { scheduleId, destinationLatitude, destinationLongitude, scheduleDetails ->
                    val bundle = bundleOf(
                        "scheduleId" to scheduleId,
                        "destinationLatitude" to destinationLatitude,
                        "destinationLongitude" to destinationLongitude
                    ).apply {
                        putParcelable("scheduleDetails", scheduleDetails)
                    }
                    navController.navigate(ROUTE_MODIFY_SCHEDULE, bundle)
                },
                moveToBackScreen = { navController.popBackStack() },
            )
        }

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

        // 일정 정보 수정 화면
        composable(
            route = ROUTE_MODIFY_SCHEDULE
        ) {
//            val destinationName = it.savedStateHandle.getStateFlow<String?>("destinationName", null).collectAsState().value
//            val destinationRoadAddress = it.savedStateHandle.getStateFlow<String?>("destinationRoadAddress", null).collectAsState().value
//            val destinationLatitude = it.savedStateHandle.getStateFlow<Double?>("destinationLatitude", null).collectAsState().value
//            val destinationLongitude = it.savedStateHandle.getStateFlow<Double?>("destinationLongitude", null).collectAsState().value
//            val selectedFriendIdsList = it.savedStateHandle.getStateFlow<List<String>?>("selectedFriendIdsList", null).collectAsState().value
            ModifyScheduleScreen(
                scheduleId = it.arguments?.getString("scheduleId") ?: "",
                initialDestinationLatitude = it.arguments?.getDouble("destinationLatitude") ?: 0.0,
                initialDestinationLongitude = it.arguments?.getDouble("destinationLongitude")
                    ?: 0.0,
                initialScheduleDetails = it.arguments?.getParcelable<DetailScheduleScreenUIState>("scheduleDetails")
                    ?: DetailScheduleScreenUIState(),
                moveToSearchLocationScreen = { navController.navigate(ROUTE_SEARCH_LOCATION) },
                moveToFriendsListScreen = { selectedFriendIdsList ->
                    val bundle = bundleOf(
                        "selectedFriendIdsList" to selectedFriendIdsList
                    )
                    navController.navigate(ROUTE_SELECT_FRIENDS, bundle)
                },
                moveToBackScreen = { navController.popBackStack() }
            )
        }

        // 친구 추가 화면
        composable(route = ROUTE_ADD_FRIEND) {
            AddFriendScreen(
                moveToBackScreen = { navController.popBackStack() }
            )
        }

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
            LocationFaboriteScreen(
                moveToBackScreen = { navController.popBackStack() },
                moveToEditLocationFaborite = { navController.navigate(ROUTE.EditLocationFaborite) }
            )
        }

        // 위치 즐겨찾기 편집 화면
        composable<ROUTE.EditLocationFaborite> {
            EditLocationFaboriteScreen(
                moveToBackScreen = { navController.popBackStack() }
            )
        }


    }
}