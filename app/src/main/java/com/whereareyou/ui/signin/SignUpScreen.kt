package com.whereareyou.ui.signin

import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.whereareyou.R
import com.whereareyou.data.Constants
import com.whereareyou.ui.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun SignUpScreen(navController: NavHostController) {
    var user_id by remember { mutableStateOf(TextFieldValue()) }
    var user_name by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var check_password by remember { mutableStateOf(TextFieldValue()) }

    var email by remember { mutableStateOf(TextFieldValue()) }
    var email_code by remember { mutableStateOf(TextFieldValue()) }


    var isInvalidId by remember { mutableStateOf(false) }
    var isInvalidPassword by remember { mutableStateOf(false) }



////////////
    val context = LocalContext.current
    val retrofit = Retrofit.Builder()
        .baseUrl("http://whereareyou-env-v11.eba-ex8qguf6.ap-northeast-2.elasticbeanstalk.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

//////////


////////
////////



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
                .fillMaxWidth()
                .height(35.dp), //
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
                )            }


            Text(
                text = "회원가입",
                fontSize = 20.sp,
                modifier = Modifier.offset(x=150.dp)
            )
        }


        Spacer(
            modifier = Modifier
                .height(40.dp)
        )


        // 이름 입력 필드
        Column() {

            Text(
                text = "이름"
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(width = 200.dp, height = 45.dp)
                    .background(Color(0xFFF5F5F6))
                    .padding(start = 16.dp, top = 10.dp)

            ) {
                BasicTextField(
                    value = user_name,
                    onValueChange = { user_name = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        textAlign = TextAlign.Left
                    ),
                    modifier = Modifier.fillMaxSize() // Fill the available space inside the Box\

                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)

            )
        }


        // 아이디 입력 필드
        Column() {
            Text(
                text = "아이디"
            )

            Row() {
                BasicTextField(

                    /// 값이 바뀌면 -> 체크 되어있떤거 false로 ㄱㄱ
                    value = user_id.text,
                    onValueChange = {
                        user_id = user_id.copy(text = it)
                        isInvalidId = it.length < 10
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        textAlign = TextAlign.Left // 텍스트 가운데 정렬
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { /* Handle Done button press if needed */ }
                    ),
                    modifier = Modifier
                        .size(width = 260.dp, height = 45.dp)
                        .background(Color(0xFFF5F5F6))
                        .padding(start = 16.dp, top = 10.dp)

                )

                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                )

                Button(
                    onClick = {
                        // 여기에 로그인 로직을 추가
                        // username 및 password를 사용하여 로그인을 처리


                        // 코루틴 스코프 내에서 비동기로 API 호출
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val response = apiService.checkUserId(user_id.toString())

                                if (response.isSuccessful) {
                                    withContext(Dispatchers.Main) {

                                        Toast.makeText(
                                            context,
                                            "API 요청 성공",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    // 성공적으로 응답을 받았을 때의 처리
                                    val responseBody = response.body()
                                    Log.d("API Response", responseBody.toString()) // API 응답 body 로그로 출력

                                    // responseBody를 사용하여 UI 업데이트 등을 수행할 수 있음
                                } else {
                                    // 실패했을 때의 처리
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(
                                            context,
                                            "API 요청 실패",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            } catch (e: Exception) {
                                // 네트워크 오류 또는 기타 예외 발생 시의 처리

                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        context,
                                        "네트워크 오류",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                Log.e("API Request", "API 요청 오류", e)

                            }
                        }
                    },

                    shape = RoundedCornerShape(3.dp),
                    modifier = Modifier
                        .size(width = 130.dp, height = 45.dp)
                        .background(Color(0xFFF5F5F6))
                ) {
                    Text("중복확인")
                }
            }

            if (isInvalidId) {
                Text(
                    text = "아이디는 10자 이상이어야 합니다.",
                    color = Color.Red
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)
            )
        }
        // 비밀번호 입력 필드

        Column() {
            Text(
                text = "비밀번호"
            )

            BasicTextField(
                value = password.text,
                onValueChange = {
                    password = password.copy(text = it)
                    isInvalidPassword = it.length < 10
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Left
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { /* Handle Done button press if needed */ }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .size(width = 200.dp, height = 45.dp)
                    .background(Color(0xFFF5F5F6))
                    .padding(start = 16.dp, top = 10.dp)

            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )

            Text(
                text = "비밀번호 확인"
            )

            BasicTextField(
                value = check_password.text,
                onValueChange = {
                    check_password = check_password.copy(text = it)
                    isInvalidPassword = it.length < 10 || it != password.text
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    textAlign = TextAlign.Left // 텍스트 가운데 정렬
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { /* Handle Done button press if needed */ }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .size(width = 200.dp, height = 45.dp)
                    .background(Color(0xFFF5F5F6))
                    .padding(start = 16.dp, top = 10.dp)

            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)
            )

            if (isInvalidPassword) {
                Text(
                    text = "비밀번호는 10자 이상이어야 하며, 확인 비밀번호와 일치해야 합니다.",
                    color = Color.Red
                )
            }
        }
        // 이메일 입력필드
        Column() {

            Text(
                text = "이메일"
            )
            Row(


            ) {
                BasicTextField(
                    value = email,
                    onValueChange = { email = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 20.sp, // 텍스트 크기 조절
                        textAlign = TextAlign.Left // 텍스트 왼쪽 정렬

                    ),

                    modifier = Modifier

                        .size(width = 260.dp, height = 45.dp) // 가로폭 200dp, 높이 90dp로 크기 조정

                        .background(Color(0xFFF5F5F6)) // 배경을 F5F5F6 색상으로 설정
                        .padding(start = 16.dp, top = 10.dp)

                )
                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                )
                Button(
                    onClick = {
                        // 여기에 로그인 로직을 추가
                    },
                    shape = RoundedCornerShape(3.dp),

                    modifier = Modifier
                        .size(width = 130.dp, height = 45.dp) // 가로폭 200dp, 높이 90dp로 크기 조정


                ) {
                    Text("인증요청")
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)

            )

            Row() {
                BasicTextField(
                    value = email_code,
                    onValueChange = { email_code = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 20.sp, // 텍스트 크기 조절
                        textAlign = TextAlign.Left // 텍스트 가운데 정렬
                    ),

                    modifier = Modifier

                        .size(width = 260.dp, height = 45.dp) // 가로폭 200dp, 높이 90dp로 크기 조정

                        .background(Color(0xFFF5F5F6)) // 배경을 F5F5F6 색상으로 설정
                        .padding(start = 16.dp, top = 10.dp)

                )
                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                )
                Button(
                    onClick = {
                        // 여기에 로그인 로직을 추가
                    },
                    shape = RoundedCornerShape(3.dp),

                    modifier = Modifier
                        .size(width = 130.dp, height = 45.dp) // 가로폭 200dp, 높이 90dp로 크기 조정
                        .background(Color(0xFFF5F5F6)) // 배경을 F5F5F6 색상으로 설정


                ) {
                    Text("확인")
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)

            )
        }


        // 로그인 버튼
        Button(
            onClick = {
                navController.navigate(Constants.ROUTE_MAIN_AGREE)
                // 여기에 로그인 로직을 추가
            },
            shape = RoundedCornerShape(3.dp),

            modifier = Modifier
                .fillMaxWidth()
                .size(width = 200.dp, height = 45.dp) // 높이 90dp로 크기 조정
                .background(color = Color.Red) // 배경색을 파란색으로 설정


        ) {
            Text("시작하기")
        }
    }
}