package com.whereareyounow.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.whereareyounow.R
import com.whereareyounow.data.Constants
import com.whereareyounow.ui.signin.FindIdScreen
import com.whereareyounow.ui.signin.FindIdSuccessScreen
import com.whereareyounow.ui.signin.FindPasswordScreen
import com.whereareyounow.ui.signin.FindPwSuccessScreen

@Composable
fun Tablayout(
    moveToSignInScreen: () -> Unit,
    navController: NavHostController,
    num: Int
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
// delete C:\Users\Owner\.android\avd\Pixel_2_API_30.avd\*.lock and try again.
    val tabs = listOf("아이디 찾기", "비밀번호 재설정")


    selectedTabIndex = num // 인덱스 넘겨주기 (탭)


    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {
        Spacer(Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
                .background(Color.White)
                .clickable {
                    moveToSignInScreen()
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_chevron__1_),
                contentDescription = "icon_button"
            )
        }
        Spacer(
            modifier = Modifier
                .height(60.dp)
        )



        TabRow(
            selectedTabIndex = selectedTabIndex,
            contentColor = Color.Black
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text = title,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold

                        )
                    },
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index

                        if (index == 0) {
                            // 아이디 재설정 탭을 클릭했을 때 네비게이션 수행
                            navController.navigate(Constants.ROUTE_MAIN_FINDID)
                        }
                        if (index == 1) {
                            // 비밀번호 재설정 탭을 클릭했을 때 네비게이션 수행
                            navController.navigate(Constants.ROUTE_MAIN_FINDPW)
                        }
                    }
                )
            }
        }
        // Content for each tab
        when (selectedTabIndex) {
            0 -> {
                FindIdScreen(navController = navController)
            }
            1 -> {
                FindPasswordScreen(navController = navController)
            }
            2 -> {
                FindIdSuccessScreen(navController = navController)
            }
            3->{
                FindPwSuccessScreen(navController = navController)
            }
        }
        

    }
}

@Composable
fun TabContent(content: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            text = content,
            modifier = Modifier.padding(16.dp)
        )
    }
}
