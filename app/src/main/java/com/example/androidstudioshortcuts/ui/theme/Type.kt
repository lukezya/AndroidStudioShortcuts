package com.example.androidstudioshortcuts.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.androidstudioshortcuts.R

val FasterOne = FontFamily(
    Font(R.font.faster_one_regular)
)

val Kanit = FontFamily(
    Font(R.font.kanit_light, FontWeight.Light)
)

val Kalam = FontFamily(
    Font(R.font.kalam_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    h3 = TextStyle(
        fontFamily = FasterOne,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp
    ),
    h5 = TextStyle(
        fontFamily = Kalam,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    body1 = TextStyle(
        fontFamily = Kanit,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    )
)