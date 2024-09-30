package com.whereareyounow.util

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

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