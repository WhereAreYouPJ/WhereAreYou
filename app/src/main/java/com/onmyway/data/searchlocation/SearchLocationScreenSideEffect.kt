package com.onmyway.data.searchlocation

sealed class SearchLocationScreenSideEffect {
    data class Toast(val text: String): SearchLocationScreenSideEffect()
}