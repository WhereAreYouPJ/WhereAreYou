package com.whereareyounow.ui.main.friend.addfriend

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.ui.component.tobbar.OneTextOneIconTobBar
import com.whereareyounow.ui.main.friend.FriendViewModel
import com.whereareyounow.ui.main.mypage.byebye.Gap
import com.whereareyounow.ui.theme.OnMyWayTheme
import com.whereareyounow.ui.theme.getColor
import com.whereareyounow.ui.theme.medium14pt
import com.whereareyounow.ui.theme.notoSanskr
import com.whereareyounow.util.clickableNoEffect

@Composable
fun AddFriendScreen(
    moveToBackScreen: () -> Unit,
    viewModel: AddFriendViewModel = hiltViewModel()
) {
    val inputId = viewModel.inputId.collectAsState().value
    val friendInfo = viewModel.friendInfo.collectAsState().value
    val buttonState = viewModel.buttonState.collectAsState().value
    AddFriendScreen(
        inputId = inputId,
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
private fun AddFriendScreen(
    inputId: String,
    updateInputId: (String) -> Unit,
    clearInputId: () -> Unit,
    friendInfo: Friend?,
    buttonState: AddFriendViewModel.ButtonState,
    searchFriend: () -> Unit,
    sendFriendRequest: () -> Unit,
    moveToBackScreen: () -> Unit
) {
    val friendViewModel : FriendViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = TOP_BAR_HEIGHT.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AddFriendScreenTopBar(moveToBackScreen)

        Gap(10)


        val searchdId = remember {
            mutableStateOf("")
        }
        FriendIdTextField(
            inputId = inputId,
            updateInputId = updateInputId,
            clearInputId = clearInputId,
            searchdId = searchdId
        )

        Gap(20)

        if (friendInfo != null) {
            UserInfoContent(
                imageUrl = friendInfo.profileImgUrl,
                userName = friendInfo.name
            )
        }

    }
}

@Composable
fun AddFriendScreenTopBar(
    moveToBackScreen: () -> Unit
) {

    OneTextOneIconTobBar(
        title = "친구 추가",
        firstIcon = R.drawable.ic_backarrow
    ) {
        moveToBackScreen()
    }
//    CustomTopBar(
//        title = "친구추가",
//        onBackButtonClicked = moveToBackScreen
//    )
}

@Composable
fun FriendIdTextField(
    inputId: String,
    updateInputId: (String) -> Unit,
    clearInputId: () -> Unit,

    searchdId: MutableState<String>
) {
    val friendViewModel : FriendViewModel = hiltViewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = "코드 검색",
            fontFamily = notoSanskr,
            fontSize = 12.sp,
            color = Color(0xFF333333),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.offset(x = 6.dp)
        )
        Gap(4)

        Row(
            modifier = Modifier

//                .border(
//                border = BorderStroke(
//                    width = 1.dp,
//                    color = Color(0xFF767676)
//                ),
//                shape = RoundedCornerShape(6.dp)
//            )

                .height(52.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = searchdId.value,
                onValueChange = {
                    searchdId.value = it
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

            ConfirmButtonDat(
                text = "확인",
                onClicked = {
                    friendViewModel.searchUser(
                        searchdId.value
                    )
                }
            )

            Text("ss")


        }


//        BasicTextField(
//            value = inputId,
//            onValueChange = { updateInputId(it) },
//            textStyle = TextStyle(fontSize = 18.sp),
//            singleLine = true,
//            decorationBox = {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .drawBehind {
//                            val borderSize = 1.dp.toPx()
//                            drawLine(
//                                color = Color(0xFF858585),
//                                start = Offset(0f, size.height),
//                                end = Offset(size.width, size.height),
//                                strokeWidth = borderSize
//                            )
//                        },
//                    contentAlignment = Alignment.CenterStart
//                ) {
//                    Box {
//                        Text(
//                            text = if (inputId == "") "친구 ID" else "",
//                            fontSize = 18.sp,
//                            color = Color(0xFFA5A5A5)
//                        )
//                        it()
//                    }
//                    Box(
//                        modifier = Modifier.fillMaxWidth(),
//                        contentAlignment = Alignment.CenterEnd
//                    ) {
//                        Image(
//                            modifier = Modifier.clickableNoEffect {
//                                clearInputId()
//                            },
//                            painter = painterResource(id = R.drawable.baseline_cancel_24),
//                            contentDescription = null,
//                            colorFilter = ColorFilter.tint(color = Color(0xFF858585))
//                        )
//                    }
//                }
//            }
//        )
    }

}

@Composable
fun UserInfoContent(
    imageUrl: String?,
    userName: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(
                    width = (0.5).dp,
                    color = Color(0xFFC3C3C6)
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .background(
                color = Color(0xFFF7F7F7),
                shape = RoundedCornerShape(10.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))
        GlideImage(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(50)),
            imageModel = { imageUrl ?: R.drawable.idle_profile },
            imageOptions = ImageOptions(contentScale = ContentScale.Crop)
        )
        Spacer(Modifier.height(40.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = userName,
                fontSize = 20.sp
            )
        }
        Spacer(Modifier.height(20.dp))
    }
}


@Composable
fun ConfirmButtonDat(
    text: String,
    onClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .width(100.dp)
            .height(52.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(getColor().brandColor)
            .clickableNoEffect {
                onClicked()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = notoSanskr,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFFFFFFF)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddFriendScreenPreview1() {
    OnMyWayTheme {
        AddFriendScreen(
            inputId = "",
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
        AddFriendScreen(
            inputId = "가나다라",
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
    UserInfoContent(
        imageUrl = "",
        userName = "sfsf"
    )
}