package com.whereareyounow.ui.navigation.friend

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.friend.addfriend.SearchAndAddFriendScreen

fun NavGraphBuilder.searchAndAddFriendScreen(navController: NavController) = composable<ROUTE.SearchAndAddFriend> {

    SearchAndAddFriendScreen(
        moveToBackScreen = { navController.popBackStack() }
    )

}