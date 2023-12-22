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

val notosanskr = FontFamily(
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
// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = notosanskr,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        platformStyle = PlatformTextStyle(
            includeFontPadding = false
        )
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)