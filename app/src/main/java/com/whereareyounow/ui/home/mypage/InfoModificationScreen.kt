package com.whereareyounow.ui.home.mypage

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.GlobalValue
import com.whereareyounow.ui.signup.CheckingButton
import com.whereareyounow.ui.signup.InputBox
import com.whereareyounow.ui.signup.InputBoxState
import com.whereareyounow.ui.signup.UserIdState
import com.whereareyounow.ui.signup.UserNameState

@Composable
fun InfoModificationScreen(
    moveToBackScreen: () -> Unit,
    viewModel: InfoModificationViewModel = hiltViewModel()
) {
    val density = LocalDensity.current.density
    val name = viewModel.name.collectAsState().value
    val inputNameState = viewModel.inputNameState.collectAsState().value
    val id = viewModel.id.collectAsState().value
    val inputIdState = viewModel.inputIdState.collectAsState().value
    val email = viewModel.email.collectAsState().value
    val profileImageUri = viewModel.profileImageUri.collectAsState().value

    // 카메라로 사진 찍어서 가져오기
    val takePhotoFromCameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { takenPhoto ->
            if (takenPhoto != null) {
                Log.e("TakePicture", "success")
            } else {
                Log.e("TakePicture", "failed")
            }
        }
    // 갤러리에서 사진 가져오기
//    val takePhotoFromAlbumLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//            Log.e("GetImage", "success")
//            Log.e("GetImage", "${uri}")
//            uri?.let {
//                viewModel.updateProfileImage(it)
//            }
//        }

    val takePhotoFromAlbumLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            Log.e("PickImage", "success")
            Log.e("PickImage", "${uri}")
            uri?.let {
                viewModel.updateProfileImageUri(uri)
            }
        }

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize()
    ) {
        TopBar(
            moveToBackScreen = moveToBackScreen,
            saveModifiedInfo = { viewModel.saveModifiedInfo(moveToBackScreen) }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height((GlobalValue.calendarViewHeight / density).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GlideImage(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(50))
                    .clickable {
                        takePhotoFromAlbumLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    },
                imageModel = { profileImageUri ?: R.drawable.account_circle_fill0_wght200_grad0_opsz24 },
                imageOptions = ImageOptions(contentScale = ContentScale.Crop,)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = "이름",
                fontSize = 20.sp
            )
            InputBox(
                hint = "사용자명",
                inputText = name,
                onValueChange = viewModel::updateName,
                inputBoxState = when (inputNameState) {
                    UserNameState.EMPTY -> InputBoxState.IDLE
                    UserNameState.SATISFIED -> InputBoxState.SATISFIED
                    UserNameState.UNSATISFIED -> InputBoxState.UNSATISFIED
                },
                isPassword = false,
                guide = when (inputNameState) {
                    UserNameState.EMPTY -> ""
                    UserNameState.SATISFIED -> "사용 가능한 사용자명입니다."
                    UserNameState.UNSATISFIED -> "사용자명은 4~10자의 한글, 영문 대/소문자 조합으로 입력해주세요."
                },
            )

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
            ) {
                // 아이디 입력창
                Box(modifier = Modifier.weight(1f)) {
                    InputBox(
                        hint = "아이디",
                        inputText = id,
                        onValueChange = viewModel::updateId,
                        inputBoxState = when (inputIdState) {
                            UserIdState.EMPTY -> InputBoxState.IDLE
                            UserIdState.SATISFIED -> InputBoxState.IDLE
                            UserIdState.UNSATISFIED -> InputBoxState.UNSATISFIED
                            UserIdState.DUPLICATED -> InputBoxState.UNSATISFIED
                            UserIdState.UNIQUE -> InputBoxState.SATISFIED
                        },
                        isPassword = false,
                        guide = when (inputIdState) {
                            UserIdState.EMPTY -> ""
                            UserIdState.SATISFIED -> "중복 확인을 해주세요."
                            UserIdState.UNSATISFIED -> "아이디는 영문 소문자로 시작하는 4~10자의 영문 소문자, 숫자 조합으로 입력해주세요."
                            UserIdState.DUPLICATED -> "이미 존재하는 아이디입니다."
                            UserIdState.UNIQUE -> "사용 가능한 아이디입니다."
                        }
                    )
                }
                Spacer(Modifier.width(10.dp))
                // 중복확인 버튼
                CheckingButton(text = "중복확인") { viewModel.checkIdDuplicate() }
            }
        }
    }
}

@Composable
fun TopBar(
    moveToBackScreen: () -> Unit,
    saveModifiedInfo: () -> Unit
) {
    val density = LocalDensity.current.density
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height((GlobalValue.topBarHeight / density).dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Image(
            modifier = Modifier
                .size((GlobalValue.topBarHeight / density / 3 * 2).dp)
                .clip(RoundedCornerShape(50))
                .clickable { moveToBackScreen() },
            painter = painterResource(id = R.drawable.arrow_back),
            contentDescription = null
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "마이페이지",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .clickable {
                    saveModifiedInfo()
                },
            text = "저장"
        )
    }
}