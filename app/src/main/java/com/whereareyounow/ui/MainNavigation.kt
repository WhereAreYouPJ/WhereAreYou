package com.whereareyounow.ui

import android.util.Log
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
import com.whereareyounow.data.ViewType
import com.whereareyounow.data.detailschedule.DetailScheduleScreenUIState
import com.whereareyounow.data.detailschedule.MemberInfo
import com.whereareyounow.data.findpw.ResultState
import com.whereareyounow.data.globalvalue.ROUTE_ADD_FRIEND
import com.whereareyounow.data.globalvalue.ROUTE_DETAIL_SCHEDULE
import com.whereareyounow.data.globalvalue.ROUTE_DETAIL_SCHEDULE_MAP
import com.whereareyounow.data.globalvalue.ROUTE_FIND_ACCOUNT
import com.whereareyounow.data.globalvalue.ROUTE_FIND_ID
import com.whereareyounow.data.globalvalue.ROUTE_FIND_ID_RESULT
import com.whereareyounow.data.globalvalue.ROUTE_FIND_PASSWORD_RESULT
import com.whereareyounow.data.globalvalue.ROUTE_FIND_PASSWORD_SUCCESS
import com.whereareyounow.data.globalvalue.ROUTE_MAIN
import com.whereareyounow.data.globalvalue.ROUTE_MODIFY_INFO
import com.whereareyounow.data.globalvalue.ROUTE_MODIFY_SCHEDULE
import com.whereareyounow.data.globalvalue.ROUTE_NEW_SCHEDULE
import com.whereareyounow.data.globalvalue.ROUTE_POLICY_AGREE
import com.whereareyounow.data.globalvalue.ROUTE_PRIVACY_POLICY
import com.whereareyounow.data.globalvalue.ROUTE_RESET_PASSWORD
import com.whereareyounow.data.globalvalue.ROUTE_SEARCH_LOCATION
import com.whereareyounow.data.globalvalue.ROUTE_SEARCH_LOCATION_MAP
import com.whereareyounow.data.globalvalue.ROUTE_SELECT_FRIENDS
import com.whereareyounow.data.globalvalue.ROUTE_SIGN_IN_METHOD_SELECTION
import com.whereareyounow.data.globalvalue.ROUTE_SIGN_IN_WITH_ACCOUNT
import com.whereareyounow.data.globalvalue.ROUTE_SIGN_UP
import com.whereareyounow.data.globalvalue.ROUTE_SIGN_UP_SUCCESS
import com.whereareyounow.data.globalvalue.ROUTE_SPLASH
import com.whereareyounow.data.globalvalue.ROUTE_TERMS_OF_SERVICE
import com.whereareyounow.ui.findaccount.FindAccountScreen
import com.whereareyounow.ui.findaccount.findid.FindIdResultScreen
import com.whereareyounow.ui.findaccount.findid.FindIdScreen
import com.whereareyounow.ui.findaccount.findpw.FindPasswordScreen
import com.whereareyounow.ui.findaccount.findpw.PasswordResetSuccessScreen
import com.whereareyounow.ui.findaccount.findpw.PasswordResettingScreen
import com.whereareyounow.ui.main.MainScreen
import com.whereareyounow.ui.main.friend.addfriend.AddFriendScreen
import com.whereareyounow.ui.main.mypage.InfoModificationScreen
import com.whereareyounow.ui.main.schedule.detailschedule.DetailScheduleMapScreen
import com.whereareyounow.ui.main.schedule.detailschedule.DetailScheduleScreen
import com.whereareyounow.ui.main.schedule.modifyschedule.ModifyScheduleScreen
import com.whereareyounow.ui.main.schedule.newschedule.NewScheduleScreen
import com.whereareyounow.ui.main.schedule.scheduleedit.FriendsListScreen
import com.whereareyounow.ui.main.schedule.scheduleedit.SearchLocationMapScreen
import com.whereareyounow.ui.main.schedule.scheduleedit.SearchLocationScreen
import com.whereareyounow.ui.signin.SignInMethodSelectionScreen
import com.whereareyounow.ui.signin.SignInWithAccountScreen
import com.whereareyounow.ui.signup.PolicyAgreeScreen
import com.whereareyounow.ui.signup.PrivacyPolicyDetailsScreen
import com.whereareyounow.ui.signup.SignUpScreen
import com.whereareyounow.ui.signup.SignUpSuccessScreen
import com.whereareyounow.ui.signup.TermsOfServiceDetailsScreen
import com.whereareyounow.ui.splash.SplashScreen
import com.whereareyounow.util.navigate

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = ROUTE_NEW_SCHEDULE
    ) {
        // 스플래시 화면
        composable(route = ROUTE_SPLASH) {
            SplashScreen(
                moveToSignInMethodSelectionScreen = {
                    navController.navigate(ROUTE_SIGN_IN_METHOD_SELECTION) {
                        popUpTo(ROUTE_SPLASH) { inclusive = true }
                    }
                },
                moveToMainScreen = {
                    navController.navigate(ROUTE_MAIN) {
                        popUpTo(ROUTE_SPLASH) { inclusive = true }
                    }
                },
            )
        }

        // 로그인 방법 선택 화면
        composable(route = ROUTE_SIGN_IN_METHOD_SELECTION) {
            SignInMethodSelectionScreen(
                moveToSignInWithAccountScreen = { navController.navigate(ROUTE_SIGN_IN_WITH_ACCOUNT) },
                moveToSignUpScreen = { navController.navigate(ROUTE_POLICY_AGREE) },
                moveToFindAccountScreen = { navController.navigate(ROUTE_FIND_ACCOUNT) }
            )
        }

        // 약관 동의 화면
        composable(route = ROUTE_POLICY_AGREE) {
            PolicyAgreeScreen(
                moveToBackScreen = { navController.popBackStack() },
                moveToSignUpScreen = { navController.navigate(ROUTE_SIGN_UP) },
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
                moveToBackScreen = { navController.popBackStack(ROUTE_POLICY_AGREE, true) },
                moveToSignUpSuccessScreen = {
                    navController.navigate(ROUTE_SIGN_UP_SUCCESS) {
                        popUpTo(ROUTE_POLICY_AGREE) { inclusive = true }
                    }
                }
            )
        }

        // 회원가입 성공 화면
        composable(route = ROUTE_SIGN_UP_SUCCESS) {
            SignUpSuccessScreen(
                moveToBackScreen = { navController.popBackStack() }
            )
        }

        // 계정으로 로그인 화면
        composable(route = ROUTE_SIGN_IN_WITH_ACCOUNT){
            SignInWithAccountScreen(
                moveToSignInMethodSelectionScreen = {
                    navController.popBackStack(ROUTE_SIGN_IN_METHOD_SELECTION, false)
                },
                moveToMainHomeScreen = {
                    navController.navigate(ROUTE_MAIN) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                },
                moveToFindAccountScreen = { navController.navigate(ROUTE_FIND_ID) },
                moveToResetPasswordScreen = { navController.navigate(ROUTE_RESET_PASSWORD) },
                moveToSignUpScreen = { navController.navigate(ROUTE_POLICY_AGREE) }
            )
        }

        // 계정 찾기 방법 선택 화면
        composable(route = ROUTE_FIND_ACCOUNT) {
            FindAccountScreen(
                moveToBackScreen = { navController.popBackStack() },
                moveToFindIdScreen = { navController.navigate(ROUTE_FIND_ID) },
                moveToResetPasswordScreen = { navController.navigate(ROUTE_RESET_PASSWORD) }
            )
        }


        // 아이디 찾기 화면
        composable(route = ROUTE_FIND_ID) {
            FindIdScreen(
                moveToFindAccountScreen = { navController.popBackStack(ROUTE_FIND_ACCOUNT, false) },
                moveToFindIdResultScreen = { searchedUserId ->
                    val bundle = bundleOf("searchedUserId" to searchedUserId)
                    navController.popBackStack(ROUTE_FIND_ACCOUNT, true)
                    navController.navigate(ROUTE_FIND_ID_RESULT, bundle)
                }
            )
        }

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
                moveToSignInScreen = { navController.popBackStack(ROUTE_SIGN_IN_WITH_ACCOUNT, false) }
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
                moveToSignInScreen = { navController.popBackStack(ROUTE_SIGN_IN_WITH_ACCOUNT, false) },
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
                moveToSignInScreen = { navController.popBackStack(ROUTE_SIGN_IN_WITH_ACCOUNT, false) }
            )
        }

        // 홈 화면
        composable(route = ROUTE_MAIN) {
            MainScreen(
                moveToAddScheduleScreen = { year, month, date ->
                    val bundle = bundleOf(
                        "year" to year,
                        "month" to month,
                        "date" to date
                    )
                    navController.navigate(ROUTE_NEW_SCHEDULE, bundle)
                },
                moveToDetailScreen = { scheduleId ->
                    val bundle = bundleOf(
                        "scheduleId" to scheduleId
                    )
                    navController.navigate(ROUTE_DETAIL_SCHEDULE, bundle)
                },
                moveToAddFriendScreen = { navController.navigate(ROUTE_ADD_FRIEND) },
                moveToAddGroupScreen = {},
                moveToSignInScreen = {
                    navController.popBackStack()
                    navController.navigate(ROUTE_SIGN_IN_WITH_ACCOUNT)
                },
                moveToModifyInfoScreen = { navController.navigate(ROUTE_MODIFY_INFO) },
                moveToMyPageScreen = {
                    // TODO
                    navController.navigate(ViewType.MyPage.name)
                }
            )
        }

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
            Log.e("SearchLocationMapScreen", "${it.arguments?.getString("lat")?.toDouble()}, ${it.arguments?.getString("lng")?.toDouble()}")
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
                passedMemberInfosList = it.arguments?.getParcelableArrayList<MemberInfo>("memberInfosList") ?: emptyList<MemberInfo>()
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
                initialDestinationLongitude = it.arguments?.getDouble("destinationLongitude") ?: 0.0,
                initialScheduleDetails = it.arguments?.getParcelable<DetailScheduleScreenUIState>("scheduleDetails") ?: DetailScheduleScreenUIState(),
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