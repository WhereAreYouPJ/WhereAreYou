package com.onmyway.data.mypage

sealed class InfoModificationScreenSideEffect {
    data class Toast(val text: String): InfoModificationScreenSideEffect()
}