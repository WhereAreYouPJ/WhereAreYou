package com.whereareyounow.ui.signin

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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.whereareyounow.data.Constants

@Composable
fun FindPwSuccessScreen(navController: NavController,signInViewModel: SignViewModel = hiltViewModel()) {
    var user_pw by remember { mutableStateOf(TextFieldValue()) }
    var user_pw_check by remember { mutableStateOf(TextFieldValue()) }

    // 아이디 비밀번호 입력 필드

    val navBackStackEntry = navController.previousBackStackEntry
    val receivedValue = navBackStackEntry?.savedStateHandle?.get<String>("userId")


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "비밀번호를 변경해 주세요.",
            style = TextStyle(fontSize = 18.sp),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(5.dp))

        Text(text = "사이트에서 사용한 적 없는 안전한 비밀번호로 변경해 주세요.", style = TextStyle(fontSize = 15.sp))

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


            Text(text = "아이디 : $receivedValue")


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

        OutlinedTextField(
            value = user_pw.text,
            onValueChange = {
                user_pw = user_pw.copy(text = it)

                // 필요한 로직 추가
            },
            singleLine = true,
            textStyle = TextStyle(fontSize = 13.sp, textAlign = TextAlign.Left),
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            placeholder = {
                Text("새 비밀번호", style = TextStyle(fontSize = 13.sp))
            },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(10.dp))





        Spacer(
            modifier = Modifier
                .width(10.dp)
        )
        OutlinedTextField(
            value = user_pw_check.text,
            onValueChange = {
                user_pw_check = user_pw_check.copy(text = it)
                // 필요한 로직 추가
            },
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 13.sp,
                textAlign = TextAlign.Left

            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            placeholder = {
                Text(
                    text = "비밀번호 확인",
                    style = TextStyle(fontSize = 13.sp)
                )

            },
            visualTransformation = PasswordVisualTransformation()

        )



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

        // 확인 버튼
        Button(
            onClick = {

                if(receivedValue!=null) {
                    signInViewModel.resetPassword(receivedValue, user_pw.text, user_pw_check.text
                    )

                }
                navController.navigate(Constants.ROUTE_MAIN_SUCCESSPW)


            },
            shape = RoundedCornerShape(3.dp),

            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)


            /*    ,

            colors = ButtonColors(Color.Red, Color.Red, Color.Red, Color.Red)
*/ // 버튼에 색 추가하는 방법
        ) {
            Text(text = "확인", style = TextStyle(fontSize = 20.sp), fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(15.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.Gray)
                .clickable {
                    navController.navigate(Constants.ROUTE_MAIN_SUCCESSPW)


                },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "취소", style = TextStyle(fontSize = 20.sp), fontWeight = FontWeight.Bold)

        }
    }

}