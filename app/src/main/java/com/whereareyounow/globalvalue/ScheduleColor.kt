package com.whereareyounow.globalvalue

import androidx.compose.ui.graphics.Color

enum class ScheduleColor(
    val colorName: String,
    val color: Color
) {
    Red("Red", Color(0xFFFFAAAA)),
    Yellow("Yellow", Color(0xFFFFE589)),
    Green("Green", Color(0xFFA3F39C)),
    Blue("Blue", Color(0xFF9CD4F3)),
    Purple("Purple", Color(0xFFCEA8FF)),
    Pink("Pink", Color(0xFFFFAEFC))
}