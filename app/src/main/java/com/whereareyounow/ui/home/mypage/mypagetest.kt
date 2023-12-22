package com.whereareyounow.ui.home.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.whereareyounow.R

@Composable
fun mypagetest() {



    //val errorIcon = painterResource(id = R.drawable.circle_error) //
    var isErrorVisible by remember { mutableStateOf(false) }
    var isCorrectVisible by remember{ mutableStateOf(false) }

    var user_id by remember { mutableStateOf(TextFieldValue()) }
    var user_email by remember { mutableStateOf(TextFieldValue()) }
    var user_email_code by remember { mutableStateOf(TextFieldValue()) }


    // 아이디 비밀번호 입력 필드N
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Spacer(Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(35.dp), //
        ) {


            Box(
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .background(Color.White)
                    .clickable {

                    }

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_chevron__1_),
                    contentDescription = "icon_button"
                )
            }


            Text(
                text = "마이페이지",
                fontSize = 20.sp,
                modifier = Modifier.offset(x = 150.dp)
            )
        }
        Spacer(
            modifier = Modifier.height(100.dp)
        )
        Text(
            text = "이름",
            style = TextStyle(fontSize = 11.sp)
        )
        Spacer(Modifier.height(10.dp))
        Row() {
            OutlinedTextField(
                value = user_id.text,
                onValueChange = {
                    user_id = user_id.copy(text = it)
                    isErrorVisible = it.isEmpty() // 특정 조건에 따라 isErrorVisible 값을 설정합니다.
                    isCorrectVisible=it.isNotEmpty()

                    // 필요한 로직 추가
                },
                singleLine = true,
                textStyle = TextStyle(fontSize = 13.sp, textAlign = TextAlign.Left),
                modifier = Modifier
                    .width(200.dp)
                    .height(45.dp),
                placeholder = {
                    Text("이름", style = TextStyle(fontSize = 13.sp))
                },
            )
            Box(
                modifier = Modifier
                    .width(30.dp)
                    .height(45.dp)
                    .background(Color.White)
                    .align(Alignment.CenterVertically)

            ) {
             /*   if (isErrorVisible) {

                    Icon(
                        painter = painterResource(id = R.drawable.circle_error),
                        contentDescription = "icon_button"
                    )
                }
                else{
                    Icon(
                        painter = painterResource(id = R.drawable.circle_check),
                        contentDescription = "icon_button"
                    )
                }*/
            }

        }


        Spacer(Modifier.height(10.dp))
        Text(
            text = "아이디",
            style = TextStyle(fontSize = 11.sp)
        )
        Spacer(Modifier.height(10.dp))

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
                modifier = Modifier.size(width = 240.dp, height = 45.dp) // 가로폭 200dp, 높이 90dp로 크기 조정

                ,
                placeholder = {
                    Text(
                        text = "아이디",
                        style = TextStyle(fontSize = 13.sp)
                    )

                }
            )
            Spacer(
                modifier = Modifier.width(10.dp)
            )
            Button(
                onClick = {
                    // 여기에 로그인 로직을 추가
                },
                shape = RoundedCornerShape(3.dp),

                modifier = Modifier
                    .size(width = 180.dp, height = 45.dp) // 가로폭 200dp, 높이 90dp로 크기 조정
                    .background(Color(0xFFF5F5F6)) // 배경을 F5F5F6 색상으로 설정


            ) {
                Text("중복확인")
            }
        }

        Spacer(Modifier.height(10.dp))
        Text(
            text = "이메일",
            style = TextStyle(fontSize = 11.sp)
        )
        Spacer(Modifier.height(10.dp))

        OutlinedTextField(
            value = user_email_code.text,
            onValueChange = {
                user_email_code = user_email_code.copy(text = it)

                // 필요한 로직 추가
            },
            singleLine = true,
            textStyle = TextStyle(fontSize = 13.sp, textAlign = TextAlign.Left),
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            placeholder = {
                Text("이메일", style = TextStyle(fontSize = 13.sp))
            },
            visualTransformation = PasswordVisualTransformation()
        )






        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )


    }


}