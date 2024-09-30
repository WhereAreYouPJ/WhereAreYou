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
    brandText: Color = Color(0xFF6236E9),
    warning: Color = Color(0xFFE13131),
    brandColor: Color = Color(0xFF7B50FF),
    brandSubColor: Color = Color(0xFFFFE253),
    calRed: Color = Color(0xFFFFAAAA),
    calYellow: Color = Color(0xFFFFE589),
    calPurple: Color = Color(0xFFCEA8FF),
    calGreen: Color = Color(0xFFA3F39C),
    dark: Color = Color(0xFF767676),
    littleDark: Color = Color(0xFFABABAB),
    thin: Color = Color(0xFFD4D4D4),
    thinner: Color = Color(0xFFEEEEEE),
    thinnest: Color = Color(0xFFDDDDDD)
): CustomColorScheme =
    CustomColorScheme(
        brandText = brandText,
        warning = warning,
        brandColor= brandColor,
        brandSubColor = brandSubColor,
        calRed = calRed,
        calYellow = calYellow,
        calPurple = calPurple,
        calGreen = calGreen,
        dark = dark,
        littleDark = littleDark,
        thin = thin,
        thinner = thinner,
        thinnest = thinnest,
    )

fun customDarkColorScheme(
    brandText: Color = Color(0xFF6236E9),
    warning: Color = Color(0xFFE13131),
    brandColor: Color = Color(0xFF7B50FF),
    brandSubColor: Color = Color(0xFFFFE253),
    calRed: Color = Color(0xFFFFAAAA),
    calYellow: Color = Color(0xFFFFE589),
    calPurple: Color = Color(0xFFCEA8FF),
    calGreen: Color = Color(0xFFA3F39C),
    dark: Color = Color(0xFF767676),
    littleDark: Color = Color(0xFFABABAB),
    thin: Color = Color(0xFFD4D4D4),
    thinner: Color = Color(0xFFEEEEEE),
    thinnest: Color = Color(0xFFDDDDDD)
): CustomColorScheme =
    CustomColorScheme(
        brandText = brandText,
        warning = warning,
        brandColor= brandColor,
        brandSubColor = brandSubColor,
        calRed = calRed,
        calYellow = calYellow,
        calPurple = calPurple,
        calGreen = calGreen,
        dark = dark,
        littleDark = littleDark,
        thin = thin,
        thinner = thinner,
        thinnest = thinnest,
    )

//fun customDarkColorScheme(
//    brightest: Color = Color.Cyan,
//    brandText: Color = Color.Cyan,
//    warning: Color = Color.Cyan,
//    brandColor: Color = Color.Cyan,
//    thinner: Color = Color.Cyan,
//    calendarRed: Color = Color.Cyan,
//    calendarYellow: Color = Color.Cyan,
//    calendarPurple: Color = Color.Cyan,
//    calendarGreen: Color = Color.Cyan,
//    thinnest: Color = Color.Cyan
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

@Stable
class CustomColorScheme(
    brandText: Color,
    warning: Color,
    brandColor: Color,
    brandSubColor: Color,
    calRed: Color,
    calYellow: Color,
    calPurple: Color,
    calGreen: Color,
    dark: Color,
    littleDark: Color,
    thin: Color,
    thinner: Color,
    thinnest: Color,
) {
    // 글씨 브랜드 컬러
    var brandText by mutableStateOf(brandText, structuralEqualityPolicy())
    // 경고
    var warning by mutableStateOf(warning, structuralEqualityPolicy())
    // 브랜드 컬러
    var brandColor by mutableStateOf(brandColor, structuralEqualityPolicy())
    // 브랜드 서브 컬러
    var brandSubColor by mutableStateOf(brandSubColor, structuralEqualityPolicy())
    // 달력 레드
    var calRed by mutableStateOf(calRed, structuralEqualityPolicy())
    // 달력 노랑
    var calYellow by mutableStateOf(calYellow, structuralEqualityPolicy())
    // 달력 퍼플
    var calPurple by mutableStateOf(calPurple, structuralEqualityPolicy())
    // 달력 그린
    var calGreen by mutableStateOf(calGreen, structuralEqualityPolicy())
    // 짙은
    var dark by mutableStateOf(dark, structuralEqualityPolicy())
    // 약간 짙은
    var littleDark by mutableStateOf(littleDark, structuralEqualityPolicy())
    // 얇은
    var thin by mutableStateOf(thin, structuralEqualityPolicy())
    // 더 얇은
    var thinner by mutableStateOf(thinner, structuralEqualityPolicy())
    // 제일 얇은
    var thinnest by mutableStateOf(thinnest, structuralEqualityPolicy())
}