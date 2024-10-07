package com.whereareyounow.ui.main.friend.addfriend

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.BOTTOM_NAVIGATION_BAR_HEIGHT
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.ui.component.button.BasicTextButton
import com.whereareyounow.ui.component.button.SizeVariableTextButton
import com.whereareyounow.ui.component.tobbar.OneTextOneIconTobBar
import com.whereareyounow.ui.main.friend.FriendViewModel
import com.whereareyounow.ui.main.mypage.byebye.Gap
import com.whereareyounow.ui.theme.OnMyWayTheme
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium12pt
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.medium20pt
import com.whereareyounow.ui.theme.notoSanskr
import com.whereareyounow.util.clickableNoEffect
import kotlinx.coroutines.delay

@Composable
fun SearchAndAddFriendScreen(
    moveToBackScreen: () -> Unit
) {
    val viewModel: AddFriendViewModel = hiltViewModel()
    val friendInfo = viewModel.friendInfo.collectAsState().value
    val buttonState = viewModel.buttonState.collectAsState().value
    SearchAndAddFriendScreen(
        updateInputId = viewModel::updateInputId,
        clearInputId = viewModel::clearInputId,
        friendInfo = friendInfo,
        buttonState = buttonState,
        searchFriend = viewModel::searchFriend,
        sendFriendRequest = viewModel::sendFriendRequest,
        moveToBackScreen = moveToBackScreen,
    )
}

@Composable
private fun SearchAndAddFriendScreen(
    updateInputId: (String) -> Unit,
    clearInputId: () -> Unit,
    friendInfo: Friend?,
    buttonState: AddFriendViewModel.ButtonState,
    searchFriend: () -> Unit,
    sendFriendRequest: () -> Unit,
    moveToBackScreen: () -> Unit
) {
    val addFriendViewModel: AddFriendViewModel = hiltViewModel()
    val searchedUserInfo = addFriendViewModel.searcedUserInfo.collectAsState().value
    val searchedCode = remember {
        mutableStateOf("")
    }
    val addFriendButtonClickedBoolean = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = TOP_BAR_HEIGHT.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AddFriendScreenTopBar(moveToBackScreen)
        Gap(10)
        SearchFriendCode(
            updateInputId = updateInputId,
            clearInputId = clearInputId,
            searchedCode = searchedCode
        )
        Gap(16)
        if (searchedUserInfo != null) {
            SearcedUserInfoContent(
                imageUrl = searchedUserInfo.profileImage,
                userName = searchedUserInfo.userName!!
            )
        }
        Spacer(Modifier.weight(1f))
        if (addFriendButtonClickedBoolean.value) {
            LaunchedEffect(addFriendButtonClickedBoolean.value) {
                delay(10000L)
                addFriendButtonClickedBoolean.value = false
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(start = 15.dp, end = 15.dp)
                    .background(
                        color = Color(0xFF333333).copy(alpha = 0.8f),
                        shape = RoundedCornerShape(16.dp),
                    ),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "친구 신청이 완료되었습니다.",
                    style = medium14pt,
                    color = Color(0xFFFFFFFF),
                    modifier = Modifier.offset(x = 16.dp)
                )
            }
            Gap(24)
        }
        BasicTextButton(
            text = "친구 신청하기",
            onClicked = {
                addFriendViewModel.requestAddFriend(searchedUserInfo!!.memberSeq.toString())
                addFriendButtonClickedBoolean.value = true
            }
        )
        Gap(BOTTOM_NAVIGATION_BAR_HEIGHT.toInt())
    }
}

@Composable
fun AddFriendScreenTopBar(
    moveToBackScreen: () -> Unit
) {
    OneTextOneIconTobBar(title = "친구 추가", firstIcon = R.drawable.ic_backarrow) {
        moveToBackScreen()
    }
}

@Composable
fun SearchFriendCode(
    updateInputId: (String) -> Unit,
    clearInputId: () -> Unit,
    searchedCode: MutableState<String>
) {
    val addFriendViewModel: AddFriendViewModel = hiltViewModel()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)
            .padding(start = 20.dp, end = 20.dp)
    ) {

        Text(
            text = "코드 검색",
            style = medium12pt,
            color = Color(0xFF333333),
            modifier = Modifier.offset(x = 6.dp)
        )

        Gap(4)

        Row(
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = searchedCode.value,
                onValueChange = {
                    searchedCode.value = it
                },
                placeholder = {
                    Text(
                        text = "ID를 입력해주세요.",
                        style = medium14pt,
                        color = Color(0xFF767676)
                    )
                },
                modifier = Modifier
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color(0xFF767676)
                        ),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .height(52.dp)
                    .width(227.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color(0xFF767676),
                    unfocusedTextColor = Color(0xFF767676),

                    ),
                textStyle = medium14pt,
                singleLine = true,
            )

            Gap(8)

            SizeVariableTextButton(
                text = "확인",
                onClicked = {
                    addFriendViewModel.searchUser(
                        searchedCode.value
                    )
                },
                buttonWidth = 100,
                buttonHeight = 52
            )
        }
        if (addFriendViewModel.searcedUserInfo.collectAsState().value != null) {
            Text(addFriendViewModel.searcedUserInfo.collectAsState().value!!.userName!!)
            Text("${addFriendViewModel.searcedUserInfo.collectAsState().value!!.memberSeq!!}")
        }

    }

}

@Composable
fun SearcedUserInfoContent(
    imageUrl: String?,
    userName: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(169.dp)
            .padding(start = 20.dp, end = 20.dp)
//            .border(
//                border = BorderStroke(
//                    width = (0.5).dp,
//                    color = Color(0xFFC3C3C6)
//                ),
//                shape = RoundedCornerShape(10.dp)
//            )
            .background(
                color = Color(0xFFF4F4F4),
                shape = RoundedCornerShape(10.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Gap(18)
        GlideImage(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(34.4.dp)),
            imageModel = { imageUrl ?: R.drawable.ic_default_profile_image },
            imageOptions = ImageOptions(contentScale = ContentScale.Crop)
        )
        Gap(8)
        Text(
            text = userName,
            style = medium20pt,
            color = Color(0xFF222222)
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddFriendScreenPreview1() {
    OnMyWayTheme {
        SearchAndAddFriendScreen(
            updateInputId = { },
            clearInputId = { },
            friendInfo = null,
            buttonState = AddFriendViewModel.ButtonState.Search,
            searchFriend = { },
            sendFriendRequest = { },
            moveToBackScreen = { }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddFriendScreenPreview2() {
    OnMyWayTheme {
        SearchAndAddFriendScreen(
            updateInputId = { },
            clearInputId = { },
            friendInfo = null,
            buttonState = AddFriendViewModel.ButtonState.Request,
            searchFriend = { },
            sendFriendRequest = { },
            moveToBackScreen = { }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewUserInfoContent() {
    SearcedUserInfoContent(
        imageUrl = "",
        userName = "sfsf"
    )
}