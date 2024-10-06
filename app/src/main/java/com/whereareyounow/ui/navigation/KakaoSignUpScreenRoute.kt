package com.whereareyounow.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.whereareyounow.data.globalvalue.ROUTE
import com.whereareyounow.ui.signup.KakaoSignUpScreen

fun NavGraphBuilder.kakaoSignUpScreenRoute(navController: NavController) = composable<ROUTE.KakaoSignUp> {

    val data: ROUTE.KakaoSignUp = it.toRoute()
    KakaoSignUpScreen(
        name = data.name,
        email = data.email,
        userId = data.userId,
        moveToBackScreen = { navController.popBackStack() },
        moveToSignUpSuccessScreen = {
            navController.navigate(ROUTE.SignUpSuccess) {
                popUpTo<ROUTE.SignInMethodSelection> { inclusive = false }
            }
        },
        moveToAccountDuplicateScreen = { accountType, email, type, userName, password ->
            navController.navigate(ROUTE.AccountDuplicate(
                accountType, email, type, userName, password
            ))
        }
    )
}