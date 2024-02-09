package com.whereareyounow.ui.home.friend.addfriend

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.GlobalValue
import com.whereareyounow.domain.entity.schedule.Friend
import com.whereareyounow.ui.component.BottomOKButton
import com.whereareyounow.ui.component.CustomTopBar
import com.whereareyounow.ui.theme.WhereAreYouTheme

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AddFriendScreenTopBar(moveToBackScreen)

        Spacer(Modifier.height(20.dp))

        FriendIdTextField(
            inputId = inputId,
            updateInputId = updateInputId,
            clearInputId = clearInputId
        )

        Spacer(Modifier.height(20.dp))

        if (friendInfo != null) {
            UserInfoContent(
                imageUrl = friendInfo.profileImgUrl,
                userName = friendInfo.name
            )
        }

        Spacer(Modifier.weight(1f))

        BottomOKButton(
            text = when (buttonState) {
                AddFriendViewModel.ButtonState.SEARCH -> "검색"
                AddFriendViewModel.ButtonState.REQUEST -> "친구추가"
            },
            onClick = when (buttonState) {
                AddFriendViewModel.ButtonState.SEARCH -> searchFriend
                AddFriendViewModel.ButtonState.REQUEST -> sendFriendRequest
            }
        )
    }
}

@Composable
fun AddFriendScreenTopBar(
    moveToBackScreen: () -> Unit
) {
    CustomTopBar(
        title = "친구추가",
        onBackButtonClicked = moveToBackScreen
    )
}

@Composable
fun FriendIdTextField(
    inputId: String,
    updateInputId: (String) -> Unit,
    clearInputId: () -> Unit
) {
    BasicTextField(
        value = inputId,
        onValueChange = { updateInputId(it) },
        textStyle = TextStyle(fontSize = 18.sp),
        singleLine = true,
        decorationBox = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawBehind {
                        val borderSize = 1.dp.toPx()
                        drawLine(
                            color = Color(0xFF858585),
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = borderSize
                        )
                    },
                contentAlignment = Alignment.CenterStart
            ) {
                Box {
                    Text(
                        text = if (inputId == "") "친구 ID" else "",
                        fontSize = 18.sp,
                        color = Color(0xFFA5A5A5)
                    )
                    it()
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Image(
                        modifier = Modifier.clickable {
                            clearInputId()
                        },
                        painter = painterResource(id = R.drawable.baseline_cancel_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = Color(0xFF858585))
                    )
                }
            }
        }
    )
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddFriendScreenPreview1() {
    WhereAreYouTheme {
        AddFriendScreen(
            inputId = "",
            updateInputId = {  },
            clearInputId = {  },
            friendInfo = null,
            buttonState = AddFriendViewModel.ButtonState.SEARCH,
            searchFriend = {  },
            sendFriendRequest = {  },
            moveToBackScreen = {  }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddFriendScreenPreview2() {
    WhereAreYouTheme {
        AddFriendScreen(
            inputId = "가나다라",
            updateInputId = {  },
            clearInputId = {  },
            friendInfo = null,
            buttonState = AddFriendViewModel.ButtonState.REQUEST,
            searchFriend = {  },
            sendFriendRequest = {  },
            moveToBackScreen = {  }
        )
    }
}