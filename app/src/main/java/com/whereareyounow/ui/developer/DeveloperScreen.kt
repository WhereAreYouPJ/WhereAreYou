package com.whereareyounow.ui.developer

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.kakao.sdk.common.util.Utility
import com.whereareyounow.domain.util.LogUtil
import com.whereareyounow.ui.component.CustomSurface

@Composable
fun DeveloperScreen() {
    val context = LocalContext.current
    val kakaoApiKey = remember {
        val keyHash = Utility.getKeyHash(context)
        LogUtil.e("keyHash", keyHash)
        keyHash
    }
    CustomSurface {
        LazyColumn {
            item {
                Column {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        TextField(
                            value = "versionCode: ${context.packageManager.getPackageInfo(context.packageName, 0).longVersionCode}",
                            onValueChange = {}
                        )
                    } else {
                        TextField(
                            value = "versionCode: ${context.packageManager.getPackageInfo(context.packageName, 0).versionCode}",
                            onValueChange = {}
                        )
                    }
                    TextField(
                        value = "kakaoApiKey: $kakaoApiKey",
                        onValueChange = {}
                    )
                }
            }
        }
    }
}