package com.whereareyounow.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.structuralEqualityPolicy
import androidx.compose.ui.graphics.Color

@Composable
@ReadOnlyComposable
fun getColor() = LocalColor.current

val LocalColor = staticCompositionLocalOf {
    customLightColorScheme()
}

fun customLightColorScheme(
    brightest: Color = Color(0xFFC5C5C5),
    brandText: Color = Color(0xFF6236E9),
    warning: Color = Color(0xFFF14242),
    brandColor: Color = Color(0xFF7B50FF),
    thinner: Color = Color(0xFFEEEEEE),
    calendarRed: Color = Color(0xFFFFAAAA),
    calendarYellow: Color = Color(0xFFFFE589),
    calendarPurple: Color = Color(0xFFCEA8FF),
    calendarGreen: Color = Color(0xFFA3F39C),
    thinnest: Color = Color(0xFFEFEFEF)
): CustomColorScheme =
    CustomColorScheme(
        brightest = brightest,
        brandText = brandText,
        warning = warning,
        brandColor = brandColor,
        thinner = thinner,
        calendarRed = calendarRed,
        calendarYellow = calendarYellow,
        calendarPurple = calendarPurple,
        calendarGreen = calendarGreen,
        thinnest = thinnest,
    )

//fun customDarkColorScheme(
//    brightest: Color = Color(0xFFC5C5C5),
//    brandText: Color = Color(0xFF6236E9),
//    warning: Color = Color(0xFFF14242),
//    brandColor: Color = Color(0xFF7B50FF),
//    thinner: Color = Color(0xFFEEEEEE),
//    calendarRed: Color = Color(0xFFFFAAAA),
//    calendarYellow: Color = Color(0xFFFFE589),
//    calendarPurple: Color = Color(0xFFCEA8FF),
//    calendarGreen: Color = Color(0xFFA3F39C),
//    thinnest: Color = Color(0xFFEFEFEF)
//): CustomColorScheme =
//    CustomColorScheme(
//        brightest = brightest,
//        brandText = brandText,
//        warning = warning,
//        brandColor = brandColor,
//        thinner = thinner,
//        calendarRed = calendarRed,
//        calendarYellow = calendarYellow,
//        calendarPurple = calendarPurple,
//        calendarGreen = calendarGreen,
//        thinnest = thinnest,
//    )

fun customDarkColorScheme(
    brightest: Color = Color.Cyan,
    brandText: Color = Color.Cyan,
    warning: Color = Color.Cyan,
    brandColor: Color = Color.Cyan,
    thinner: Color = Color.Cyan,
    calendarRed: Color = Color.Cyan,
    calendarYellow: Color = Color.Cyan,
    calendarPurple: Color = Color.Cyan,
    calendarGreen: Color = Color.Cyan,
    thinnest: Color = Color.Cyan
): CustomColorScheme =
    CustomColorScheme(
        brightest = brightest,
        brandText = brandText,
        warning = warning,
        brandColor = brandColor,
        thinner = thinner,
        calendarRed = calendarRed,
        calendarYellow = calendarYellow,
        calendarPurple = calendarPurple,
        calendarGreen = calendarGreen,
        thinnest = thinnest,
    )

@Stable
class CustomColorScheme(
    brightest: Color,
    brandText: Color,
    warning: Color,
    brandColor: Color,
    thinner: Color,
    calendarRed: Color,
    calendarYellow: Color,
    calendarPurple: Color,
    calendarGreen: Color,
    thinnest: Color,
) {
    var brightest by mutableStateOf(brightest, structuralEqualityPolicy())
    var brandText by mutableStateOf(brandText, structuralEqualityPolicy())
    var warning by mutableStateOf(warning, structuralEqualityPolicy())
    var brandColor by mutableStateOf(brandColor, structuralEqualityPolicy())
    var thinner by mutableStateOf(thinner, structuralEqualityPolicy())
    var calendarRed by mutableStateOf(calendarRed, structuralEqualityPolicy())
    var calendarYellow by mutableStateOf(calendarYellow, structuralEqualityPolicy())
    var calendarPurple by mutableStateOf(calendarPurple, structuralEqualityPolicy())
    var calendarGreen by mutableStateOf(calendarGreen, structuralEqualityPolicy())
    var thinnest by mutableStateOf(thinnest, structuralEqualityPolicy())
}