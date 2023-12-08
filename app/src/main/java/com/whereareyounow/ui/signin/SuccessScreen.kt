package com.whereareyounow.ui.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.whereareyounow.R
import com.whereareyounow.data.Constants

@Composable
fun SuccessScreen(navController: NavHostController){
    val checkIcon = painterResource(id = R.drawable.vector__1_)




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Spacer(
            modifier = Modifier
                .height(150.dp)
        )




        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
        ) {


            Image(
                painter = checkIcon,
                contentDescription = null, // 콘텐츠 설명 (필요한 경우 추가)
                modifier = Modifier.size(78.dp) // 아이콘의 크기 지정
            )
            Spacer(
                modifier = Modifier
                    .height(15.dp)
            )
            Text(
                text = "회원가입 완료",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(
                modifier = Modifier
                    .height(15.dp)
            )
            Text(
                text = "시작화면에서 로그인해주세요.",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                        color = Color(0xFF7A7878)

            )
            Text(
                text = "최초 로그인 이후에는 접속 시에 자동 로그인이 활성화 됩니다.",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7A7878)

            )
        }

        Button(
            onClick = {
                navController.navigate(Constants.ROUTE_MAIN_SIGNIN)
                // 여기에 로그인 로직을 추가
                // username 및 password를 사용하여 로그인을 처리
            },
            shape = RoundedCornerShape(3.dp),

            modifier = Modifier
                .fillMaxWidth()
                .size(width = 200.dp, height = 45.dp) // 높이 90dp로 크기 조정

        ) {
            Text("확인")
        }

    }

}