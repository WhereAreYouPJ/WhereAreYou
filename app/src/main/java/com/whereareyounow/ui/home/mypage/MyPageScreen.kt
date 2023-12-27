package com.whereareyounow.ui.home.mypage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyounow.R
import com.whereareyounow.data.GlobalValue
import java.io.File

@Composable
fun MyPageScreen(
    paddingValues: PaddingValues,
    moveToStartScreen: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
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
                viewModel.updateProfileImage(uri)
            }
        }

    val density = LocalDensity.current.density
    val name = viewModel.name.collectAsState().value
    val email = viewModel.email.collectAsState().value
    val profileImageUri = viewModel.profileImageUri.collectAsState().value

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                brush = Brush.horizontalGradient(listOf(Color(0xFF362A9C), Color(0xFF214BB7))),
                center = Offset(size.width / 2, GlobalValue.calendarViewHeight + GlobalValue.topBarHeight - size.width),
                radius = size.width,
                style = Fill
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((GlobalValue.topBarHeight / density).dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "설정",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((GlobalValue.calendarViewHeight / density).dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                GlideImage(
                    modifier = Modifier
                        .width(((GlobalValue.calendarViewHeight / density) / 2).dp)
                        .height(((GlobalValue.calendarViewHeight / density) / 2).dp)
                        .clip(RoundedCornerShape(50)),
                    imageModel = { profileImageUri ?: R.drawable.account_circle_fill0_wght200_grad0_opsz24 },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.FillWidth,
                    )
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = name,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Text(
                    text = email,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Light
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(top = ((GlobalValue.calendarViewHeight + GlobalValue.topBarHeight) / density).dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(start = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "일반",
                    fontSize = 24.sp
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((0.6).dp)
                    .background(color = Color.Black)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable {
                        takePhotoFromAlbumLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                    .padding(start = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "회원정보변경",
                    fontSize = 24.sp
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable {
                        viewModel.signOut(
                            moveToStartScreen = moveToStartScreen
                        )
                    }
                    .padding(start = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "로그아웃",
                    fontSize = 24.sp,
                    color = Color.Red
                )
            }
//            val imagePath = viewModel.imageUri.collectAsState().value
//            GlideImage(
//                modifier = Modifier
//                    .width(100.dp)
//                    .height(100.dp)
//                    .clip(RoundedCornerShape(50)),
//                imageModel = { imagePath ?: R.drawable.account_circle_fill0_wght200_grad0_opsz24 },
//                imageOptions = ImageOptions(
//                    contentScale = ContentScale.FillWidth,
//                )
//            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
private fun Uri.parseBitmap(context: Context): Bitmap {
    return when (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        true -> {
            val source = ImageDecoder.createSource(context.contentResolver, this)
            ImageDecoder.decodeBitmap(source)
        }
        false -> {
            MediaStore.Images.Media.getBitmap(context.contentResolver, this)
        }
    }
}

private fun Uri.getFile(context: Context): File? {
    return path?.let { File(it) }
}