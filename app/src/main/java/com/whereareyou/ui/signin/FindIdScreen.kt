package com.whereareyou.ui.signin

import androidx.compose.foundation.background
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavHostController

@Composable
fun FindIdScreen(
    navController: NavHostController,
    signInViewModel: SignViewModel = hiltViewModel()
) {
    var user_email by remember { mutableStateOf(TextFieldValue()) }
    var user_email_code by remember { mutableStateOf(TextFieldValue()) }
    // 아이디 비밀번호 입력 필드N


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))


        Row() {


            OutlinedTextField(
                value = user_email.text,
                onValueChange = {
                    user_email = user_email.copy(text = it)
                    // 필요한 로직 추가
                },
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 13.sp,
                    textAlign = TextAlign.Left

                ),
                modifier = Modifier
                    .size(width = 280.dp, height = 45.dp) // 가로폭 200dp, 높이 90dp로 크기 조정

                ,
                placeholder = {
                    Text(
                        text = "이메일 주소",
                        style = TextStyle(fontSize = 13.sp)
                    )

                }
            )
            Spacer(
                modifier = Modifier
                    .width(10.dp)
            )
            Button(
                onClick = {
                          signInViewModel.checkauthenticateEmail("chanhue467@gmail.com")

                },
                shape = RoundedCornerShape(3.dp),

                modifier = Modifier
                    .size(width = 140.dp, height = 45.dp) // 가로폭 200dp, 높이 90dp로 크기 조정
                    .background(Color(0xFFF5F5F6)) // 배경을 F5F5F6 색상으로 설정


            ) {
                Text("인증")
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = user_email_code.text,
            onValueChange = {
                user_email_code = user_email_code.copy(text = it)

            },
            singleLine = true,
            textStyle = TextStyle(fontSize = 13.sp, textAlign = TextAlign.Left),
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            placeholder = {
                Text("비밀번호를 입력하세요", style = TextStyle(fontSize = 13.sp))
            },
            visualTransformation = PasswordVisualTransformation()
        )






        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )


        // 로그인 버튼
        Button(
            onClick = {

                //navController.navigate(Constants.ROUTE_MAIN_FINDIDSUCCESS)


            },
            shape = RoundedCornerShape(3.dp),

            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)


        ) {
            Text(text = "확인", style = TextStyle(fontSize = 20.sp), fontWeight = FontWeight.Bold)
        }
    }

}

/*    ,

           colors = ButtonColors(Color.Red, Color.Red, Color.Red, Color.Red)
*/ // 버튼에 색 추가하는 방법