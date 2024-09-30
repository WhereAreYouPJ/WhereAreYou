package com.whereareyounow.data.mypage

sealed class InfoModificationScreenSideEffect {
    data class Toast(val text: String): InfoModificationScreenSideEffect()
}