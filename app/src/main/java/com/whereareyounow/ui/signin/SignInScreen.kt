package com.whereareyounow.ui.signin

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.VisualTransformation
import com.whereareyounow.R


@Composable
fun SignInScreen(
    moveToStartScreen: () -> Unit,
    moveToMainHomeScreen: () -> Unit,
    moveToFindIdScreen: () -> Unit,
    moveToFindPWScreen: () -> Unit,
    moveToBackScreen: () -> Unit,
    signInViewModel: SignViewModel = hiltViewModel(),
    viewModel: SignInViewModel = hiltViewModel()
) {
    var user_name by remember { mutableStateOf(TextFieldValue()) }
    var user_password by remember { mutableStateOf(TextFieldValue()) }

    var rememberMe by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize()
    ) {
        SignInScreenTopBar(moveToBackScreen)
        Spacer(
            modifier = Modifier
                .height(150.dp)
        )
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
        Spacer(Modifier.height(60.dp))

        // 아이디 입력
        val inputId = viewModel.inputId.collectAsState().value
        InputBox(
            hint = "아이디",
            inputText = inputId,
            onValueChange = viewModel::updateInputId,
            isPassword = false
        )

        Spacer(Modifier.height(10.dp))

        // 비밀번호 입력
        val inputPassword = viewModel.inputPassword.collectAsState().value
        InputBox(
            hint = "비밀번호",
            inputText = inputPassword,
            onValueChange = viewModel::updateInputPassword,
            isPassword = false
        )

        Spacer(Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(
                    color = Color(0xFF2A2550),
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable {
                    viewModel.signIn(moveToMainHomeScreen)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "로그인",
                fontSize = 26.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun InputBox(
    hint: String,
    inputText: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean
) {
    BasicTextField(
        modifier = Modifier.fillMaxWidth(),
        value = inputText,
        onValueChange = { onValueChange(it) },
        textStyle = TextStyle(fontSize = 20.sp),
        singleLine = true,
        visualTransformation = when (isPassword) {
            true -> PasswordVisualTransformation()
            false -> VisualTransformation.None
        },
        decorationBox = {
            Box(
                modifier = Modifier
                    .height(50.dp)
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color.Black
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(start = 20.dp, end = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                it()
                if (inputText == "") {
                    Text(
                        text = hint,
                        fontSize = 20.sp,
                        color = Color(0xFFBCBCBC)
                    )
                }
            }
        }
    )
}