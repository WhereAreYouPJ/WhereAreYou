package com.whereareyounow.data.findaccount

sealed class FindAccountEmailVerificationScreenSideEffect {
    data class Toast(val text: String): FindAccountEmailVerificationScreenSideEffect()
}