package com.whereareyounow.ui.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.ViewType
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.data.globalvalue.ROUTE_DETAIL_PROFILE
import com.whereareyounow.data.globalvalue.ROUTE_MODIFY_INFO
import com.whereareyounow.ui.main.MainScreen
import com.whereareyounow.util.navigate

fun NavGraphBuilder.mainScreenRoute(navController: NavController) = composable<ROUTE.Main> {
    MainScreen(
        moveToAddScheduleScreen = { year, month, date ->
            navController.navigate(ROUTE.AddSchedule(year, month, date))
        },
        moveToDetailScheduleScreen = { scheduleId ->
            navController.navigate(ROUTE.DetailSchedule(scheduleSeq = scheduleId))
        },
        moveToAddFriendScreen = { navController.navigate(ROUTE.SearchAndAddFriend) },
        moveToAddGroupScreen = {},
        moveToAddFeedScreen = { navController.navigate(ROUTE.AddFeed) },
        moveToSignInMethodSelectionScreen = {
            navController.popBackStack()
            navController.navigate(ROUTE.SignInMethodSelection)
        },
        moveToNotificationScreen = {
            navController.navigate(ROUTE.Notification)
        },
        moveToMapScreen = {
            navController.navigate(ROUTE.DetailScheduleMap(it))
        },
        moveToModifyInfoScreen = { navController.navigate(ROUTE_MODIFY_INFO) },
        moveToMyPageScreen = {
            navController.navigate(ViewType.MyPage.name)
        },
        moveToDetailProfileScreen = { friendImagrUrl, friendName ->
            val bundle = bundleOf(
                "friendImagrUrl" to friendImagrUrl,
                "friendName" to friendName
            )
//                    friendViewModel.friendsList[]
            navController.navigate(ROUTE_DETAIL_PROFILE , bundle)
        },
        moveToMyInfoScreen = {
            navController.navigate(ROUTE.MyInfo)
        },
        moveToFavoriteLocationScreen = { navController.navigate(ROUTE.FavoriteLocation) },
        moveToFeedBookmarks = { navController.navigate(ROUTE.FeedBookMark) },
        moveToFeedSaved = { navController.navigate(ROUTE.FeedStore) },
        moveToAccoument = {navController.navigate(ROUTE.Announcement)},
        moveToAsk = {navController.navigate(ROUTE.Ask)},
        moveToBye = { navController.navigate(ROUTE.Bye1) }
    )
}