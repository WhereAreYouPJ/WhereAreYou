package com.whereareyounow.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.whereareyounow.R


//val fontFamily = FontFamily(
//    Font(R.font.nanumsquareneo_light, FontWeight.Light),
//    Font(R.font.nanumsquareneo_regular, FontWeight.Normal),
//    Font(R.font.nanumsquareneo_bold, FontWeight.Bold),
//    Font(R.font.nanumsquareneo_extrabold, FontWeight.ExtraBold),
//    Font(R.font.nanumsquareneo_heavy, FontWeight.Black),
//)
val ttangs = FontFamily(
    Font(R.font.ttangs_light, FontWeight.Light),
    Font(R.font.ttangs_medium, FontWeight.Medium),
    Font(R.font.ttangs_bold, FontWeight.Bold)
)

val lato = FontFamily(
    Font(R.font.lato_thin, FontWeight.Thin),
    Font(R.font.lato_light, FontWeight.Light),
    Font(R.font.lato_regular, FontWeight.Normal),
    Font(R.font.lato_bold, FontWeight.Bold),
    Font(R.font.lato_black, FontWeight.Black),
)

val nanumSquareNeo = FontFamily(
    Font(R.font.nanumsquareneo_light, FontWeight.Light),
    Font(R.font.nanumsquareneo_regular, FontWeight.Normal),
    Font(R.font.nanumsquareneo_bold, FontWeight.Bold),
    Font(R.font.nanumsquareneo_extrabold, FontWeight.ExtraBold),
    Font(R.font.nanumsquareneo_heavy, FontWeight.Black)
)

val notoSanskr = FontFamily(
    Font(R.font.notosanskr_thin, FontWeight.Thin),
    Font(R.font.notosanskr_extralight, FontWeight.ExtraLight),
    Font(R.font.notosanskr_light, FontWeight.Light),
    Font(R.font.notosanskr_regular, FontWeight.Normal),
    Font(R.font.notosanskr_medium, FontWeight.Medium),
    Font(R.font.notosanskr_semibold, FontWeight.SemiBold),
    Font(R.font.notosanskr_bold, FontWeight.Bold),
    Font(R.font.notosanskr_extrabold, FontWeight.ExtraBold),
    Font(R.font.notosanskr_black, FontWeight.Black)
)

val nanumSquare = FontFamily(
    Font(R.font.nanumsquare_light, FontWeight.Light),
    Font(R.font.nanumsquare_regular, FontWeight.Normal),
    Font(R.font.nanumsquare_bold, FontWeight.Bold),
    Font(R.font.nanumsquare_extrabold, FontWeight.ExtraBold),
)

val nanumSquareAc = FontFamily(
    Font(R.font.nanumsquare_ac_light, FontWeight.Light),
    Font(R.font.nanumsquare_ac_regular, FontWeight.Normal),
    Font(R.font.nanumsquare_ac_bold, FontWeight.Bold),
    Font(R.font.nanumsquare_ac_extrabold, FontWeight.ExtraBold),
)

val pretendard = FontFamily(
    Font(R.font.pretendard_thin, FontWeight.Thin),
    Font(R.font.pretendard_extralight, FontWeight.ExtraLight),
    Font(R.font.pretendard_light, FontWeight.Light),
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold),
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_extrabold, FontWeight.ExtraBold),
    Font(R.font.pretendard_black, FontWeight.Black)
)
// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = notoSanskr,
        platformStyle = PlatformTextStyle(false)
    ),
    displayMedium = TextStyle(
        fontFamily = notoSanskr,
        platformStyle = PlatformTextStyle(false)
    ),
    displaySmall = TextStyle(
        fontFamily = notoSanskr,
        platformStyle = PlatformTextStyle(false)
    ),
    headlineLarge = TextStyle(
        fontFamily = notoSanskr,
        platformStyle = PlatformTextStyle(false)
    ),
    headlineMedium = TextStyle(
        fontFamily = notoSanskr,
        platformStyle = PlatformTextStyle(false)
    ),
    headlineSmall = TextStyle(
        fontFamily = notoSanskr,
        platformStyle = PlatformTextStyle(false)
    ),
    titleLarge = TextStyle(
        fontFamily = notoSanskr,
        platformStyle = PlatformTextStyle(false)
    ),
    titleMedium = TextStyle(
        fontFamily = notoSanskr,
        platformStyle = PlatformTextStyle(false)
    ),
    titleSmall = TextStyle(
        fontFamily = notoSanskr,
        platformStyle = PlatformTextStyle(false)
    ),
    bodyLarge = TextStyle(
        fontFamily = notoSanskr,
        platformStyle = PlatformTextStyle(false)
    ),
    bodyMedium = TextStyle(
        fontFamily = notoSanskr,
        platformStyle = PlatformTextStyle(false)
    ),
    bodySmall = TextStyle(
        fontFamily = notoSanskr,
        platformStyle = PlatformTextStyle(false)
    ),
    labelLarge = TextStyle(
        fontFamily = notoSanskr,
        platformStyle = PlatformTextStyle(false)
    ),
    labelMedium = TextStyle(
        fontFamily = notoSanskr,
        platformStyle = PlatformTextStyle(false)
    ),
    labelSmall = TextStyle(
        fontFamily = notoSanskr,
        platformStyle = PlatformTextStyle(false)
    ),
)

private const val baseFontSize = 10
val medium10pt = TextStyle(
    fontSize = (baseFontSize + 0).sp,
    fontWeight = FontWeight.Medium,
    fontFamily = notoSanskr,
    platformStyle = PlatformTextStyle(false)
)
val medium12pt = TextStyle(
    fontSize = (baseFontSize + 2).sp,
    fontWeight = FontWeight.Medium,
    fontFamily = notoSanskr,
    platformStyle = PlatformTextStyle(false)
)
val medium14pt = TextStyle(
    fontSize = (baseFontSize + 4).sp,
    fontWeight = FontWeight.Medium,
    fontFamily = notoSanskr,
    platformStyle = PlatformTextStyle(false)
)
val medium16pt = TextStyle(
    fontSize = (baseFontSize + 6).sp,
    fontWeight = FontWeight.Medium,
    fontFamily = notoSanskr,
    platformStyle = PlatformTextStyle(false)
)
val medium18pt = TextStyle(
    fontSize = (baseFontSize + 8).sp,
    fontWeight = FontWeight.Medium,
    fontFamily = notoSanskr,
    platformStyle = PlatformTextStyle(false)
)
val bold18pt = TextStyle(
    fontSize = (baseFontSize + 8).sp,
    fontWeight = FontWeight.Bold,
    fontFamily = notoSanskr,
    platformStyle = PlatformTextStyle(false)
)
val medium20pt = TextStyle(
    fontSize = (baseFontSize + 10).sp,
    fontWeight = FontWeight.Medium,
    fontFamily = notoSanskr,
    platformStyle = PlatformTextStyle(false)
)
val bold22pt = TextStyle(
    fontSize = (baseFontSize + 12).sp,
    fontWeight = FontWeight.Bold,
    fontFamily = notoSanskr,
    platformStyle = PlatformTextStyle(false)
)