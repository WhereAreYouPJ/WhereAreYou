package com.whereareyounow.test

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whereareyounow.util.clickableNoEffect


@Composable
fun RecompositionTestComposable(
    viewModel: TestViewModel = hiltViewModel()
) {
    val textData1 = viewModel.textData1.collectAsState().value
    val textData2 = viewModel.textData2.collectAsState().value
    val textData3 = viewModel.textData3.collectAsState().value
    val screenState = viewModel.screenState.collectAsState().value
    Text(
        modifier = Modifier
            .semantics { contentDescription = "text1" }
            .fillMaxWidth()
            .height(30.dp),
        text = "1: $textData1"
    )
    Box(
        modifier = Modifier
            .semantics { contentDescription = "button1" }
            .size(100.dp)
            .background(Color.Cyan)
            .clickableNoEffect {
                viewModel.updateTextData1()
            }
    )
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        text = "2: $textData2"
    )
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Cyan)
            .clickable {
                viewModel.updateTextData2()
            }
    )
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        text = "3: $textData3"
    )
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Cyan)
            .clickable {
                viewModel.updateTextData3()
            }
    )
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        text = "4: ${screenState.textData4}"
    )
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Cyan)
            .clickable {
                viewModel.updateTextData4()
            }
    )
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        text = "5: ${screenState.textData5}"
    )
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Cyan)
            .clickable {
                viewModel.updateTextData5()
            }
    )
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        text = "6: ${screenState.textData6}"
    )
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.Cyan)
            .clickable {
                viewModel.updateTextData6()
            }
    )
}