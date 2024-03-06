package com.whereareyounow.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.whereareyounow.data.ROUTE_ADD_FRIEND
import com.whereareyounow.data.ROUTE_DETAIL_SCHEDULE
import com.whereareyounow.data.ROUTE_DETAIL_SCHEDULE_MAP
import com.whereareyounow.data.ROUTE_FIND_ID
import com.whereareyounow.data.ROUTE_FIND_ID_RESULT
import com.whereareyounow.data.ROUTE_FIND_PASSWORD
import com.whereareyounow.data.ROUTE_FIND_PASSWORD_RESULT
import com.whereareyounow.data.ROUTE_FIND_PASSWORD_SUCCESS
import com.whereareyounow.data.ROUTE_HOME
import com.whereareyounow.data.ROUTE_MODIFY_INFO
import com.whereareyounow.data.ROUTE_MODIFY_SCHEDULE
import com.whereareyounow.data.ROUTE_NEW_SCHEDULE
import com.whereareyounow.data.ROUTE_POLICY_AGREE
import com.whereareyounow.data.ROUTE_PRIVACY_POLICY
import com.whereareyounow.data.ROUTE_SEARCH_LOCATION
import com.whereareyounow.data.ROUTE_SEARCH_LOCATION_MAP
import com.whereareyounow.data.ROUTE_SELECT_FRIENDS
import com.whereareyounow.data.ROUTE_SIGN_IN
import com.whereareyounow.data.ROUTE_SIGN_UP
import com.whereareyounow.data.ROUTE_SIGN_UP_SUCCESS
import com.whereareyounow.data.ROUTE_SPLASH
import com.whereareyounow.data.ROUTE_TERMS_OF_SERVICE
import com.whereareyounow.data.detailschedule.DetailScheduleScreenUIState
import com.whereareyounow.data.detailschedule.MemberInfo
import com.whereareyounow.data.findpw.ResultState
import com.whereareyounow.ui.findid.FindIdResultScreen
import com.whereareyounow.ui.findid.FindIdScreen
import com.whereareyounow.ui.findpw.FindPasswordScreen
import com.whereareyounow.ui.findpw.PasswordResetSuccessScreen
import com.whereareyounow.ui.findpw.PasswordResettingScreen
import com.whereareyounow.ui.home.HomeContent
import com.whereareyounow.ui.home.friend.addfriend.AddFriendScreen
import com.whereareyounow.ui.home.mypage.InfoModificationScreen
import com.whereareyounow.ui.home.schedule.detailschedule.DetailScheduleMapScreen
import com.whereareyounow.ui.home.schedule.detailschedule.DetailScheduleScreen
import com.whereareyounow.ui.home.schedule.modifyschedule.ModifyScheduleScreen
import com.whereareyounow.ui.home.schedule.newschedule.NewScheduleScreen
import com.whereareyounow.ui.home.schedule.scheduleedit.FriendsListScreen
import com.whereareyounow.ui.home.schedule.scheduleedit.SearchLocationMapScreen
import com.whereareyounow.ui.home.schedule.scheduleedit.SearchLocationScreen
import com.whereareyounow.ui.signin.SignInScreen
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
            FindIdScreen(
                moveToSignInScreen = { navController.popBackStack(ROUTE_SIGN_IN, false) },
                moveToFindIdResultScreen = { searchedUserId ->
                    val bundle = bundleOf("searchedUserId" to searchedUserId)
                    navController.popBackStack(ROUTE_SIGN_IN, false)
                    navController.navigate(ROUTE_FIND_ID_RESULT, bundle)
                }
            )
        }

        // 아이디 찾기 결과 화면
        composable(route = ROUTE_FIND_ID_RESULT) {
            FindIdResultScreen(
                searchedUserId = it.arguments?.getString("searchedUserId") ?: "",
                moveToSignInScreen = { navController.popBackStack(ROUTE_SIGN_IN, false) }
            )
        }

        // 비밀번호 찾기 화면
        composable(route = ROUTE_FIND_PASSWORD) {
            FindPasswordScreen(
                moveToSignInScreen = { navController.popBackStack() },
                moveToPasswordResettingScreen = { userId, resultState ->
                    val bundle = bundleOf(
                        "userId" to userId,
                        "resultState" to resultState.name
                    )
                    navController.popBackStack(ROUTE_SIGN_IN, false)
                    navController.navigate(ROUTE_FIND_PASSWORD_RESULT, bundle)
                },
            )
        }

        // 비밀번호 찾기 결과 화면
        composable(route = ROUTE_FIND_PASSWORD_RESULT) {
            PasswordResettingScreen(
                userId = it.arguments?.getString("userId") ?: "",
                resultState = ResultState.valueOf(it.arguments?.getString("resultState") ?: "OK"),
                moveToSignInScreen = { navController.popBackStack(ROUTE_SIGN_IN, false) },
                moveToPasswordResetSuccessScreen = {
                    navController.navigate(ROUTE_FIND_PASSWORD_SUCCESS) {
                        popUpTo(ROUTE_SIGN_IN) { inclusive = false }
                    }
                }
            )
        }

        // 비밀번호 찾기 성공 화면
        composable(route = ROUTE_FIND_PASSWORD_SUCCESS) {
            PasswordResetSuccessScreen(
                moveToSignInScreen = { navController.popBackStack(ROUTE_SIGN_IN, false) }
            )
        }

        // 홈 화면
        composable(route = ROUTE_HOME) {
            HomeContent(
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
                    navController.navigate(ROUTE_SIGN_IN)
                },
                moveToModifyInfoScreen = { navController.navigate(ROUTE_MODIFY_INFO) }
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