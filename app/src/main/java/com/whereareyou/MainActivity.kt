package com.whereareyou

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
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

