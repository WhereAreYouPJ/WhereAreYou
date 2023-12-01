package com.whereareyou.ui.home.mypage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.whereareyou.R
import java.io.File

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyPageScreen(
    paddingValues: PaddingValues,
    moveToStartScreen: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val context = LocalContext.current
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
    val takePhotoFromAlbumLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            Log.e("GetImage", "success")
            Log.e("GetImage", "${uri}")
            uri?.let {
                viewModel.updateProfileImage(it)
            }
        }

    Column {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(color = Color.Yellow)
                .clickable {
                    viewModel.signOut(
                        moveToStartScreen = moveToStartScreen
                    )
                },
        ) {
            Text(
                text = "로그아웃"
            )
        }
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(color = Color.Cyan)
                .clickable {
                    val takePhotoFromAlbumIntent =
                        Intent(
                            Intent.ACTION_GET_CONTENT,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                        ).apply {
                            type = "image/*"
                            action = Intent.ACTION_GET_CONTENT
                            putExtra(
                                Intent.EXTRA_MIME_TYPES,
                                arrayOf("image/jpeg", "image/png", "image/bmp", "image/webp")
                            )
                            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
                        }
//                    takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent)
                    takePhotoFromAlbumLauncher.launch("image/*")
//                viewModel.updateProfileImage()
                }
        ) {
            Text(
                text = "회원정보변경"
            )
        }
        val imagePath = viewModel.imageUri.collectAsState().value
        GlideImage(
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clip(shape = RoundedCornerShape(50)),
            imageModel = { imagePath ?: R.drawable.account_circle_fill0_wght200_grad0_opsz24 },
            imageOptions = ImageOptions(
                contentScale = ContentScale.FillHeight,
            )
        )
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