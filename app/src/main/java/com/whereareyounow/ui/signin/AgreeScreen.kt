package com.whereareyounow.ui.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.whereareyounow.R
import com.whereareyounow.data.Constants

@Composable
fun AgreeScreen(navController: NavHostController) {

    var checkBox1State by remember { mutableStateOf(false) }
    var checkBox2State by remember { mutableStateOf(false) }
    var checkBox3State by remember { mutableStateOf(false) }

    var allChecked by remember { mutableStateOf(false) }
    val checkIcon = painterResource(id = R.drawable.vector__1_)
    val uncheckIcon = painterResource(id = R.drawable.vector) //
    val check = painterResource(id = R.drawable.check1)
    val checked = painterResource(id = R.drawable.checked1)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Spacer(
            modifier = Modifier
                .height(15.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
        ) {

            Box(
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .background(Color.White)
                    .clickable {
                        navController.navigate(Constants.ROUTE_MAIN_AGREE)

                    }

            ) {
                Icon(
                    painter = painterResource(id =R.drawable.icon_chevron__1_),
                    contentDescription = "icon_button"
                )
            }

            Spacer(
                modifier = Modifier
                    .height(55.dp)
            )

            Text(
                text = "약관에 동의하시면",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "회원가입이 완료됩니다.",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(140.dp))

        // 모두 동의 체크 박스
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                allChecked = !allChecked
                checkBox1State = allChecked
                checkBox2State = allChecked
                checkBox3State = allChecked
            }
        ) {
            Image(
                painter = if (allChecked) checkIcon else uncheckIcon,
                contentDescription = null, // 콘텐츠 설명 (필요한 경우 추가)
                modifier = Modifier.size(24.dp) // 아이콘의 크기 지정
            )
            Spacer(Modifier.width(10.dp))

            Text("모두 동의하기")
        }

        Spacer(Modifier.height(40.dp))


        // 체크 박스 1
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { checkBox1State = !checkBox1State }
        ) {

            Image(
                painter = if (checkBox1State) checked else check,
                contentDescription = null, // 콘텐츠 설명 (필요한 경우 추가)
                modifier = Modifier.size(24.dp) // 아이콘의 크기 지정
            )

            Text(
                text = "서비스 이용약관 (필수)", modifier = Modifier
                    .padding(end = 8.dp) // 이미지와 텍스트 사이의 간격 조절
                    .weight(1f) // 나머지 공간을 균등하게 분배
            )


            ClickableText(
                text = AnnotatedString("보기"),
                onClick = { offset ->
                    // 이동 // navController.navigate(Constants.ROUTE_MAIN_FINDID)
                },
                style = TextStyle(fontSize = 16.sp)
            )

        }
        Spacer(Modifier.height(25.dp))

        // 체크 박스 2
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { checkBox2State = !checkBox2State }
        ) {
            Image(
                painter = if (checkBox2State) checked else check,
                contentDescription = null, // 콘텐츠 설명 (필요한 경우 추가)
                modifier = Modifier.size(24.dp) // 아이콘의 크기 지정
            )
            Text(
                "개인정보 처리방침 (필수)", modifier = Modifier
                    .padding(end = 8.dp) // 이미지와 텍스트 사이의 간격 조절
                    .weight(1f) // 나머지 공간을 균등하게 분배
            )

            ClickableText(
                text = AnnotatedString("보기"),
                onClick = { offset ->
                    // 이동 // navController.navigate(Constants.ROUTE_MAIN_FINDID)
                },
                style = TextStyle(fontSize = 16.sp)
            )

        }
        Spacer(Modifier.height(25.dp))

        // 체크 박스 3
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { checkBox3State = !checkBox3State }
        ) {
            Image(
                painter = if (checkBox3State) checked else check,
                contentDescription = null, // 콘텐츠 설명 (필요한 경우 추가)
                modifier = Modifier.size(24.dp) // 아이콘의 크기 지정
            )
            Text(
                text = "위치기반 서비스 이용약관 (필수)",
                modifier = Modifier
                    .padding(end = 8.dp) // 이미지와 텍스트 사이의 간격 조절
                    .weight(1f) // 나머지 공간을 균등하게 분배)
            )

            ClickableText(
                text = AnnotatedString("보기"),
                onClick = { offset ->
                    // 이동 // navController.navigate(Constants.ROUTE_MAIN_FINDID)
                },
                style = TextStyle(fontSize = 16.sp)
            )
        }
        Spacer(Modifier.height(16.dp))


        Spacer(Modifier.height(16.dp))
        // 로그인 버튼
        Button(
            onClick = {
                navController.navigate(Constants.ROUTE_MAIN_SUCCESS)
                // 여기에 로그인 로직을 추가
                // username 및 password를 사용하여 로그인을 처리
            },
            shape = RoundedCornerShape(3.dp),

            modifier = Modifier
                .fillMaxWidth()
                .size(width = 200.dp, height = 55.dp) // 높이 90dp로 크기 조정

        ) {
            Text(
                text = "동의하고 가입하기",
                style = TextStyle(fontSize = 20.sp),
                fontWeight = FontWeight.Bold
            )

        }
    }


}

