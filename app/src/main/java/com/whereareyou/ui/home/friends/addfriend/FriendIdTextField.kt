package com.whereareyou.ui.home.friends.addfriend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FriendIdTextField(
    viewModel: AddFriendViewModel = hiltViewModel()
) {
    val inputId = viewModel.inputId.collectAsState().value
    BasicTextField(
        value = inputId,
        onValueChange = { viewModel.updateInputId(it) },
        textStyle = TextStyle(fontSize = 20.sp),
        decorationBox = {
            Box(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth()
                    .background(color = Color.Cyan)
            ) { it() }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun FriendIdTextFieldPreview() {
    FriendIdTextField()
}