package com.whereareyou

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.whereareyou.ui.MainNavigation
import com.whereareyou.ui.theme.WhereAreYouTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: GlobalViewModel by lazy {
        ViewModelProvider(this)[GlobalViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setMyMemberId("1ee27e79-c410-44b2-86ef-a2d2b0f17bf3")
        viewModel.updateGlobalValue()

        setContent {
            WhereAreYouTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val systemUiController = rememberSystemUiController()

                    systemUiController.setStatusBarColor(
                        color = Color(0xFFFAFAFA),
                        darkIcons = true
                    )
                    MainNavigation()
                }
            }
        }
    }
}

