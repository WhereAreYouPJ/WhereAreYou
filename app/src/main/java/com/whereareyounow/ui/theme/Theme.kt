package com.whereareyounow.ui.theme

import android.app.Activity
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Density
import androidx.core.view.WindowCompat
import com.whereareyounow.globalvalue.globalDensity

//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
//
//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)

@Composable
fun OnMyWayTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    // 다크모드 대응용
    val colorScheme = when {
        darkTheme -> customDarkColorScheme()
        else -> customLightColorScheme()
    }

    val view = LocalView.current
    val customDensity = LocalContext.current.resources.displayMetrics.widthPixels.toFloat() / 375f

    if (!view.isInEditMode) {
        SideEffect {
            // 타입 캐스팅이 가능한지 확인. 다이얼로그는 이걸 확인 안해주면 에러가 발생.
            if (view.context is Activity) {
                val window = (view.context as Activity).window
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = true
            }
        }
    }

    val rippleIndication = androidx.compose.material.ripple.rememberRipple()

    CompositionLocalProvider(
        LocalColor provides colorScheme,
        LocalDensity provides Density(customDensity, fontScale = 1f),
        LocalIndication provides rippleIndication,
        LocalRippleTheme provides CustomRippleTheme
    ) {
        globalDensity = LocalDensity.current.density
        content()
    }

//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = Typography,
//        content = content
//    )
}

@Immutable
private object CustomRippleTheme : androidx.compose.material.ripple.RippleTheme {
    @Deprecated("Super method deprecated")
    @Composable
    override fun defaultColor() = LocalContentColor.current

    @Deprecated("Super method deprecated")
    @Composable
    override fun rippleAlpha() = DefaultRippleAlpha
}

private val DefaultRippleAlpha = RippleAlpha(
    pressedAlpha = 0.12f,
    focusedAlpha = 0.12f,
    draggedAlpha = 0.16f,
    hoveredAlpha = 0.08f
)