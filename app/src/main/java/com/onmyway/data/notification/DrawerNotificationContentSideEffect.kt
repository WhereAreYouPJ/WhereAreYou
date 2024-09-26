package com.onmyway.data.notification

sealed class DrawerNotificationContentSideEffect {
    data class Toast(val text: String): DrawerNotificationContentSideEffect()

}