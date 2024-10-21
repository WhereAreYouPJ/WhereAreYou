package com.whereareyounow.data.myinfo

sealed class MyInfoScreenSideEffect {
    data class Toast(val text: String): MyInfoScreenSideEffect()
}