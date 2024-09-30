package com.whereareyounow.data.calendar

sealed interface UIState {
    object Loading : UIState
    data class Data<out T: Content>(val data: T) : UIState
    data class Error(val message: String) : UIState
}

interface Content