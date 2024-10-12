package com.whereareyounow.util

sealed interface UiState<out T> {
    data class Loading<T>(val prevData: T?) : UiState<T>
    data class Success<T>(val data: T) : UiState<T>
    data class Failure<T>(val msg: String) : UiState<T>
}