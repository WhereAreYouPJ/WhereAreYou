package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.main.mypage.location.FavoriteLocationScreen

fun NavGraphBuilder.favoriteLocationScreenRoute(navController: NavController) = composable<ROUTE.FavoriteLocation> {
    FavoriteLocationScreen(
        moveToBackScreen = { /*TODO*/ },
        moveToEditLocationFavorite = {}
    )
}