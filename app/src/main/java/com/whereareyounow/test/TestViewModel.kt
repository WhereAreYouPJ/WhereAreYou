package com.whereareyounow.test

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(val app: Application) : AndroidViewModel(app) {

    private val _textData1 = MutableStateFlow(0)
    val textData1 = _textData1.asStateFlow()
    private val _textData2 = MutableStateFlow(0)
    val textData2 = _textData2.asStateFlow()
    private val _textData3 = MutableStateFlow(0)
    val textData3 = _textData3.asStateFlow()
    private val _screenState = MutableStateFlow(ScreenState())
    val screenState = _screenState.asStateFlow()

    fun updateTextData1() {
        _textData1.update { it + 1 }
    }

    fun updateTextData2() {
        _textData2.update { it + 1 }
    }

    fun updateTextData3() {
        _textData3.update { it + 1 }
    }

    fun updateTextData4() {
        _screenState.update {
            it.copy(
                textData4 = it.textData4 + 1
            )
        }
    }

    fun updateTextData5() {
        _screenState.update {
            it.copy(
                textData5 = it.textData5 + 1
            )
        }
    }

    fun updateTextData6() {
        _screenState.update {
            it.copy(
                textData6 = it.textData6 + 1
            )
        }
    }
}