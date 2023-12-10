package com.whereareyounow.ui.signin

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.*
import com.whereareyounow.R


@Composable
fun LoginScreen(
    moveToStartScreen: () -> Unit,
    moveToMainHomeScreen: () -> Unit,
    moveToFindIdScreen: () -> Unit,
    moveToFindPWScreen: () -> Unit,
    moveToBackScreen: () -> Unit,
    signInViewModel: SignViewModel = hiltViewModel()
) {
    var user_name by remember { mutableStateOf(TextFieldValue()) }
    var user_password by remember { mutableStateOf(TextFieldValue()) }

    var rememberMe by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Spacer(
            modifier = Modifier
                .height(10.dp)
        )
        Box(
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
                .background(Color.White)
                .clickable { moveToBackScreen() }

        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_chevron__1_),
                contentDescription = "icon_button"
            )
        }



        Spacer(
            modifier = Modifier
                .height(100.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp),
        ) {
            Text(
                text = "안녕하세요 :)",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "지금어디? 입니다.",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        // 아이디 비밀번호 입력 필드
        Column() {
            OutlinedTextField(
                value = user_name.text,
                onValueChange = {
                    user_name = user_name.copy(text = it)
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
                        text = "아이디를 입력하세요.",
                        style = TextStyle(fontSize = 13.sp)
                    )

                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = user_password.text,
                onValueChange = {
                    user_password = user_password.copy(text = it)

                    // 필요한 로직 추가
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
        }


        // 로그인 유지 체크박스
        /*Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Checkbox(
                checked = rememberMe,
                onCheckedChange = {
                    rememberMe = it
                }
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(text = "로그인 상태 유지", style = TextStyle(fontSize = 14.sp))
        }*/

        // 로그인 버튼

        Button(
            onClick = {

                Log.e("SignViewModel",user_name.text)
                Log.e("SignViewModel",user_password.text)

                signInViewModel.LogIn(user_name.text, user_password.text) { isLoginSuccess ->
                    Log.d("sign",isLoginSuccess.toString())
                    if (isLoginSuccess) {
                        moveToMainHomeScreen()
                    } else {
                        Log.d("sign","error!!")
//                        Toast.makeText(
//                            context,
//                            "아이디 또는 비밀번호가 일치하지 않습니다.",
//                            Toast.LENGTH_LONG
//                        ).show()

                    }
                }




                // 여기에 로그인 로직을 추가
                // username 및 password를 사용하여 로그인을 처리
            },
            shape = RoundedCornerShape(3.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = "로그인", style = TextStyle(fontSize = 20.sp), fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(4.dp))

        /*Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()

        ) {


            ClickableText(
                text = AnnotatedString(" 아이디 찾기"),
                onClick = { moveToFindIdScreen() },
                style = TextStyle(fontSize = 16.sp)
            )

            Text(
                text = "|",
                style = TextStyle(fontSize = 16.sp)
            )


            ClickableText(
                text = AnnotatedString("비밀번호 찾기"),
                onClick = { moveToFindPWScreen() },
                style = TextStyle(
                    fontSize = 16.sp,
                )
            )


        }*/


    }
}