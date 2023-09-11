package com.whereareyou.util

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.ui.unit.IntOffset

object AnimationUtil {
    val enterTransition = fadeIn(
        animationSpec = TweenSpec(
            durationMillis = 300,
            delay = 300
        )
    )

    val exitTransition = fadeOut(
        animationSpec = TweenSpec(
            durationMillis = 300
        )
    )
}