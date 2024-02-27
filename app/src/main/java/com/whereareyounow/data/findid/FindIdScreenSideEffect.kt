package com.whereareyounow.data.findid

sealed class FindIdScreenSideEffect {
    data class Toast(val text: String): FindIdScreenSideEffect()
}