package com.whereareyou.ui.signin

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
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.whereareyou.data.Constants

@Composable
fun FindIdSuccessScreen(navController: NavHostController) {
    var user_email by remember { mutableStateOf(TextFieldValue()) }
    // 아이디 비밀번호 입력 필드

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "고객님의 정보와 일치하는 아이디 목록입니다.", style = TextStyle(fontSize = 18.sp), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(5.dp))

        Text(text = "아이디를 먼저 선택한 후, 로그인 또는 비밀번호 찾기 버튼을", style = TextStyle(fontSize = 15.sp))
        Text(text = "눌러주세요.", style = TextStyle(fontSize = 15.sp))

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )

        HorizontalDivider(thickness = 1.dp, color = Color.Black)

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )

        Row() {




            Spacer(
                modifier = Modifier
                    .width(10.dp)
            )

        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )

        HorizontalDivider(thickness = 1.dp, color = Color.Black)








        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )

        // 로그인 버튼
        Button(
            onClick = {
                navController.navigate(Constants.ROUTE_MAIN_SIGNIN)

            },
            shape = RoundedCornerShape(3.dp),

            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)

            /*    ,

            colors = ButtonColors(Color.Red, Color.Red, Color.Red, Color.Red)
*/ // 버튼에 색 추가하는 방법
        ) {
            Text(text = "로그인", style = TextStyle(fontSize = 20.sp), fontWeight = FontWeight.Bold)
        }
        Spacer(modifier =Modifier.height(15.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.Gray)
                .clickable {
                           navController.navigate(Constants.ROUTE_MAIN_FINDPW)

                },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "비밀번호 찾기", style = TextStyle(fontSize = 20.sp), fontWeight = FontWeight.Bold)

        }
    }

}