package com.whereareyounow.ui.main.mypage

import android.net.Uri
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.globalvalue.CALENDAR_VIEW_HEIGHT
import com.whereareyounow.data.globalvalue.TOP_BAR_HEIGHT
import com.whereareyounow.data.infomodification.UserIdState
import com.whereareyounow.data.infomodification.UserNameState
import com.whereareyounow.data.mypage.InfoModificationScreenSideEffect
import com.whereareyounow.ui.component.CustomTextField
import com.whereareyounow.ui.component.CustomTextFieldState
import com.whereareyounow.ui.signup.Guideline
import com.whereareyounow.ui.signup.component.CheckingButton
import com.whereareyounow.ui.theme.WhereAreYouTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.withContext

@Composable
fun InfoModificationScreen(
    moveToBackScreen: () -> Unit,
    viewModel: InfoModificationViewModel = hiltViewModel()
) {
    val inputUserName = viewModel.inputUserName.collectAsState().value
    val inputUserNameState = viewModel.inputUserNameState.collectAsState().value
    val inputUserId = viewModel.inputUserId.collectAsState().value
    val inputUserIdState = viewModel.inputUserIdState.collectAsState().value
    val email = viewModel.email.collectAsState().value
    val profileImageUri = viewModel.profileImageUri.collectAsState().value
    val infoModificationScreenSideEffectFlow = viewModel.infoModificationScreenSideEffectFlow
    InfoModificationScreen(
        infoModificationScreenSideEffectFlow = infoModificationScreenSideEffectFlow,
        inputUserName = inputUserName,
        updateInputUserName = viewModel::updateInputUserName,
        inputUserNameState = inputUserNameState,
        inputUserId = inputUserId,
        updateInputUserId = viewModel::updateInputUserId,
        checkIdDuplicate = viewModel::checkIdDuplicate,
        inputUserIdState = inputUserIdState,
        email = email,
        profileImageUri = profileImageUri,
        updateProfileImageUri = viewModel::updateProfileImageUri,
        saveModifiedInfo = viewModel::saveModifiedInfo,
        moveToBackScreen = moveToBackScreen
    )
}

@Composable
private fun InfoModificationScreen(
    infoModificationScreenSideEffectFlow: SharedFlow<InfoModificationScreenSideEffect>,
    inputUserName: String,
    updateInputUserName: (String) -> Unit,
    inputUserNameState: UserNameState,
    inputUserId: String,
    updateInputUserId: (String) -> Unit,
    checkIdDuplicate: () -> Unit,
    inputUserIdState: UserIdState,
    email: String,
    profileImageUri: String?,
    updateProfileImageUri: (Uri) -> Unit,
    saveModifiedInfo: (() -> Unit) -> Unit,
    moveToBackScreen: () -> Unit
) {
    val density = LocalDensity.current.density
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        infoModificationScreenSideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is InfoModificationScreenSideEffect.Toast -> {
                    withContext(Dispatchers.Main) { Toast.makeText(context, sideEffect.text, Toast.LENGTH_SHORT).show() }
                }
            }
        }
    }
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
                updateProfileImageUri(uri)
            }
        }

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxSize()
    ) {
        TopBar(
            moveToBackScreen = moveToBackScreen,
            saveModifiedInfo = { saveModifiedInfo(moveToBackScreen) }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height((CALENDAR_VIEW_HEIGHT / density).dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GlideImage(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(50))
                    .clickable {
                        takePhotoFromAlbumLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                imageModel = { profileImageUri ?: R.drawable.idle_profile },
                imageOptions = ImageOptions(contentScale = ContentScale.Crop,)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            // 사용자명 입력
            Text(
                text = "이름",
                fontSize = 20.sp
            )
            Spacer(Modifier.height(10.dp))
            UserNameTextField(
                inputUserName = inputUserName,
                updateInputUserName = updateInputUserName,
                inputUserNameState = inputUserNameState
            )
            if (inputUserNameState == UserNameState.Unsatisfied) {
                Guideline(text = "사용자명은 2~4자의 한글, 영문 대/소문자 조합으로 입력해주세요.")
            }

            Spacer(Modifier.height(20.dp))

            // 아이디 입력
            Text(
                text = "아이디",
                fontSize = 20.sp
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
            ) {
                // 아이디 입력창
                Box(modifier = Modifier.weight(1f)) {
                    UserIdTextField(
                        inputUserId = inputUserId,
                        updateInputUserId = updateInputUserId,
                        inputUserIdState = inputUserIdState
                    )
                }
                Spacer(Modifier.width(10.dp))
                // 중복확인 버튼
                CheckingButton(text = "중복확인", isActive = true) { checkIdDuplicate() }
            }
            if (inputUserIdState == UserIdState.Unsatisfied) {
                Guideline(text = "아이디는 영문 소문자로 시작하는 4~10자의 영문 소문자, 숫자 조합으로 입력해주세요.")
            }
            if (inputUserIdState == UserIdState.Duplicated) {
                Guideline(text = "이미 존재하는 아이디입니다.")
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
            .height((TOP_BAR_HEIGHT / density).dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Image(
            modifier = Modifier
                .size((TOP_BAR_HEIGHT / density / 2).dp)
                .clip(RoundedCornerShape(50))
                .clickable { moveToBackScreen() },
            painter = painterResource(id = R.drawable.ic_back),
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
                fontSize = 26.sp
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

@Composable
private fun UserNameTextField(
    inputUserName: String,
    updateInputUserName: (String) -> Unit,
    inputUserNameState: UserNameState
) {
    CustomTextField(
        hint = "사용자명",
        inputText = inputUserName,
        onValueChange = updateInputUserName,
        warningText = "",
        textFieldState = when (inputUserNameState) {
            UserNameState.Empty -> CustomTextFieldState.Idle
            UserNameState.Satisfied -> CustomTextFieldState.Satisfied
            UserNameState.Unsatisfied -> CustomTextFieldState.Unsatisfied
        }
    )
}

@Composable
private fun UserIdTextField(
    inputUserId: String,
    updateInputUserId: (String) -> Unit,
    inputUserIdState: UserIdState
) {
    CustomTextField(
        hint = "아이디",
        inputText = inputUserId,
        onValueChange = updateInputUserId,
        warningText = "",
        textFieldState = when (inputUserIdState) {
            UserIdState.Empty -> CustomTextFieldState.Idle
            UserIdState.Satisfied -> CustomTextFieldState.Idle
            UserIdState.Unsatisfied -> CustomTextFieldState.Unsatisfied
            UserIdState.Duplicated -> CustomTextFieldState.Unsatisfied
            UserIdState.Unique -> CustomTextFieldState.Satisfied
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun InfoModificationScreenPreview() {
    WhereAreYouTheme {
        InfoModificationScreen(
            infoModificationScreenSideEffectFlow = MutableSharedFlow(),
            inputUserName = "아이디",
            updateInputUserName = {},
            inputUserNameState = UserNameState.Empty,
            inputUserId = "유저 아이디",
            updateInputUserId = {},
            checkIdDuplicate = {},
            inputUserIdState = UserIdState.Empty,
            email = "example@gmail.com",
            profileImageUri = "",
            updateProfileImageUri = {},
            saveModifiedInfo = {},
            moveToBackScreen = {}
        )
    }
}