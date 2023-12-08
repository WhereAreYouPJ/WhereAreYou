package com.whereareyounow.ui.signin


import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.whereareyounow.R
import com.whereareyounow.data.Constants

fun isValidUserId(input: String): Boolean {
    val regex = Regex("^[a-z][a-z0-9]{4,11}$")
    return regex.matches(input)
}

fun checkPasswordConditions(password: String): Boolean {
    val lengthCondition = password.length in 6..20
    val lowercaseRegex = Regex(".*[a-z].*")
    val uppercaseRegex = Regex(".*[A-Z].*")
    val digitRegex = Regex(".*\\d.*")
    val specialCharRegex = Regex("[!@#\$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*")

    val containsLowercase = lowercaseRegex.matches(password)
    val containsUppercase = uppercaseRegex.matches(password)
    val containsDigit = digitRegex.matches(password)
    val containsSpecialChar = specialCharRegex.matches(password)

    val conditionsMet = listOf(
        containsLowercase,
        containsUppercase,
        containsDigit,
        containsSpecialChar
    ).count { it } >= 2

    return lengthCondition && conditionsMet
}

@Composable
fun SignUpScreen(
    navController: NavHostController,
    signInViewModel: SignViewModel = hiltViewModel()
) {
    var user_id by remember { mutableStateOf(TextFieldValue()) }
    var user_name by remember { mutableStateOf(TextFieldValue()) }
    var check_password by remember { mutableStateOf(TextFieldValue()) }

    var password by remember { mutableStateOf(TextFieldValue()) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue()) }
    var isPasswordValid by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf(TextFieldValue()) }
    var emailCode by remember { mutableStateOf(TextFieldValue()) }
    var isEmailChecked by remember { mutableStateOf(false) }

    var isInvalidId by remember { mutableStateOf(false) }
    var isInvalidPassword by remember { mutableStateOf(false) }
    var isIdDuplicate by remember { mutableStateOf(false) }

    // 버튼 클릭 가능 여부를 저장할 변수
    var isButtonEnabled by remember { mutableStateOf(false) }
    var isButtonClicked by remember { mutableStateOf(false) }



    var EmailCodeText by remember { mutableStateOf(false) }


    // 값이 입력되고 검증이 되었을경우 true
    var name_pass by remember { mutableStateOf(false) }
    var id_pass by remember { mutableStateOf(false) }
    var password_pass by remember { mutableStateOf(false) }
    var email_pass by remember { mutableStateOf(true) } //임시로 true 설정

    val context = LocalContext.current


////////////


//////////
    //checkauthenticateEmail().
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
                        navController.navigate(Constants.ROUTE_MAIN_SIGNIN)

                    }

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_chevron__1_),
                    contentDescription = "icon_button"
                )
            }


            Text(
                text = "회원가입",
                fontSize = 20.sp,
                modifier = Modifier.offset(x = 150.dp)
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
                    onValueChange = {
                        user_name = it
                        name_pass = true
                    },
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
                        isInvalidId = !isValidUserId(it)
                        isButtonEnabled = it.isNotEmpty() && !isInvalidId
                        isButtonClicked = false

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
                        if (isButtonEnabled) {

                            // 여기에 로그인 로직을 추가
                            // username 및 password를 사용하여 로그인을 처리
                            // 오류떄문에 임시로 string 값 넣어 놈.
                            signInViewModel.checkIdDuplicated(user_id.text) { isIdDuplicated ->
                                Log.d("check", isIdDuplicated.toString())
                                isIdDuplicate = isIdDuplicated
                                isButtonClicked = true


                            }// 중복 아닐경우 true


                        }

                    },
                    enabled = isButtonEnabled, // 버튼 활성/비활성 상태 변경

                    shape = RoundedCornerShape(3.dp),
                    modifier = Modifier
                        .size(width = 130.dp, height = 45.dp)
                        .background(Color(0xFFF5F5F6))
                ) {
                    Text("중복확인")
                }
            }
            if (!user_id.text.isNotEmpty() || (user_id.text.isNotEmpty() && isInvalidId && !isButtonClicked)) {
                Text(
                    text = "영문 소문자로 시작하는 5~12자의 아이디를 입력해주세요.",
                    color = Color.Red,
                    fontSize = 15.sp

                )
            }

            if (isIdDuplicate && isButtonClicked) {
                Text(
                    text = "사용 가능한 아이디입니다.",
                    color = Color.Green
                )
                id_pass = true // 검증완료이므로 true 변환

            } else if (!isIdDuplicate && isButtonClicked && user_id.text.isNotEmpty() && !isInvalidId) {
                Text(
                    text = "중복된 아이디입니다.",
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
                    //isPasswordValid = validatePassword(it, confirmPassword.text)

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
            if (password.text.isNotEmpty()) {
                val passwordConditionsMet = checkPasswordConditions(password.text)
                if (!passwordConditionsMet) {
                    Text(
                        text = "영문 대문자와 소문자, 숫자, 특수문자 중 2가지 이상을 조합하여 6~20자로 입력해주세요.",
                        color = Color.Red
                    )
                    password_pass = false

                } else {
                    Text(
                        text = "사용가능한 비밀번호입니다.",
                        color = Color.Green
                    )
                }

            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
            )

            Text(
                text = "비밀번호 확인"
            )

            BasicTextField(
                value = confirmPassword.text,
                onValueChange = {
                    confirmPassword = confirmPassword.copy(text = it)
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
            if (confirmPassword.text.isNotEmpty()) {
                val passwordsMatch = password.text == confirmPassword.text
                if (passwordsMatch) {
                    Text(
                        text = "비밀번호가 일치합니다",
                        color = Color.Green

                    )
                    password_pass = true

                } else {
                    Text(
                        text = "비밀번호가 일치하지 않습니다",
                        color = Color.Red
                    )
                    password_pass = false

                }
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)
            )


        }
        // 이메일 입력필드
        Column() {
            Text(text = "이메일")

            Row {
                BasicTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        textAlign = TextAlign.Left
                    ),
                    modifier = Modifier
                        .size(width = 260.dp, height = 45.dp)
                        .background(Color(0xFFF5F5F6))
                        .padding(start = 16.dp, top = 10.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))

                if (!isEmailChecked) {
                    Button(
                        onClick = {
                            // 여기에 중복 확인 로직 추가
                            signInViewModel.checkEmailDuplicate(email.text) {
                                Emailunduplicated ->
                                if(Emailunduplicated==true){

                                    isEmailChecked = true

                                }
                                else{
                                    Toast.makeText(
                                        context,
                                        "이메일이 중복되었습니다.",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    isEmailChecked = false

                                }
                            }

                        },
                        shape = RoundedCornerShape(3.dp),
                        modifier = Modifier
                            .size(width = 130.dp, height = 45.dp)
                    ) {
                        Text("중복확인")
                    }
                } else {
                    Button(
                        onClick = {
                            // 여기에 인증 요청 로직 추가
                            signInViewModel.checkauthenticateEmail(email.text)
                        },
                        shape = RoundedCornerShape(3.dp),
                        modifier = Modifier
                            .size(width = 130.dp, height = 45.dp)
                            .background(Color(0xFFF5F5F6))
                    ) {
                        Text("인증요청")
                    }
                }
            }

            Spacer(modifier = Modifier.fillMaxWidth().height(15.dp))

            if (isEmailChecked) {
                Row {
                    BasicTextField(
                        value = emailCode,
                        onValueChange = {
                            emailCode = it
                        },
                        singleLine = true,
                        textStyle = TextStyle(
                            fontSize = 20.sp,
                            textAlign = TextAlign.Left
                        ),
                        modifier = Modifier
                            .size(width = 260.dp, height = 45.dp)
                            .background(Color(0xFFF5F5F6))
                            .padding(start = 16.dp, top = 10.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = {
                            signInViewModel.checkauthenticateEmailCode(email.text, emailCode.text.toInt()){
                                EmailCode->
                                if(EmailCode==true){
                                    EmailCodeText=true
                                }
                                else{
                                    Toast.makeText(
                                        context,
                                        "인증코드가 일치하지 않습니다. ",
                                        Toast.LENGTH_LONG
                                    ).show()


                                }

                            }
                        },
                        shape = RoundedCornerShape(3.dp),
                        modifier = Modifier
                            .size(width = 130.dp, height = 45.dp)
                            .background(Color(0xFFF5F5F6))
                    ) {
                        Text("확인")
                    }
                }
            }
        }

        if (EmailCodeText==true) {
            Text(
                text = "인증되었습니다.",
                color = Color.Green
            )
            email_pass = true // 검증완료이므로 true 변환

        }
        Spacer(modifier = Modifier.fillMaxWidth().height(15.dp))

        // 로그인 버튼
        Button(
            onClick = {
                /*  signInViewModel.signup(user_name.text,user_id.text,check_password.text,email.text)*/

                if (name_pass && id_pass && password_pass && email_pass) {
                    Log.d("pass1", name_pass.toString())
                    Log.d("pass2", id_pass.toString())
                    Log.d("pass3", password_pass.toString())
                    Log.d("pass4", email_pass.toString())
                    signInViewModel.signup(
                        user_name.text,
                        user_id.text,
                        confirmPassword.text,
                        email.text
                    )

                    navController.navigate(Constants.ROUTE_MAIN_SIGNIN)
                }
                // 여기에 로그인 로직을 추가
            },
            shape = RoundedCornerShape(3.dp),

            modifier = Modifier
                .fillMaxWidth()
                .size(width = 200.dp, height = 45.dp) // 높이 90dp로 크기 조정
                .background(color = if (name_pass && id_pass && password_pass && email_pass) Color.Red else Color.Gray), // 조건에 따라 배경색 변경
            enabled = name_pass && id_pass && password_pass && email_pass // 버튼의 활성/비활성 상태 설정


        ) {
            Text("시작하기")
        }
    }
}