package com.example.travenorowner.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.travenorowner.R

@Composable
private fun sfUiDisplayFontFamily(): FontFamily {
    return FontFamily(
        Font(R.font.sf_ui_display_regular, FontWeight.Normal),
        Font(R.font.sf_ui_display_semibold, FontWeight.SemiBold),
        Font(R.font.sf_ui_display_medium, FontWeight.Medium),

        )
}

@Composable
private fun sfProRoundedFontFamily(): FontFamily {
    return FontFamily(
        Font(R.font.sf_pro_rounded_medium, FontWeight.Medium),
        Font(R.font.sf_pro_rounded_regular, FontWeight.Normal),
        Font(R.font.sf_pro_rounded_semibold, FontWeight.SemiBold),
    )
}

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
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

object AppTypography{

    val titleLarge: TextStyle
        @Composable get() = TextStyle(
            fontSize = 20.sp,
            lineHeight = 28.sp,
            fontFamily = sfUiDisplayFontFamily(),
            fontWeight = FontWeight.SemiBold,
        )

    val titleMediumSfPro: TextStyle
        @Composable get() = TextStyle(
            fontSize = 18.sp,
            lineHeight = 22.sp,
            fontFamily = sfProRoundedFontFamily(),
            fontWeight = FontWeight.SemiBold,
        )

    val titleMediumSfUi: TextStyle
        @Composable get() = TextStyle(
            fontSize = 18.sp,
            lineHeight = 22.sp,
            fontFamily = sfUiDisplayFontFamily(),
            fontWeight = FontWeight.SemiBold,
        )

    val titleSmall: TextStyle
        @Composable get() = TextStyle(
            fontSize = 14.sp,
            lineHeight = 16.sp,
            fontFamily = sfProRoundedFontFamily(),
            fontWeight = FontWeight.SemiBold,
        )

    val labelLargeSemiBold: TextStyle
        @Composable get() = TextStyle(
            fontSize = 16.sp,
            lineHeight = 20.sp,
            fontFamily = sfUiDisplayFontFamily(),
            fontWeight = FontWeight.SemiBold,
        )

    val labelLargeRegular: TextStyle
        @Composable get() = TextStyle(
            fontSize = 16.sp,
            lineHeight = 16.sp,
            fontFamily = sfUiDisplayFontFamily(),
            fontWeight = FontWeight.Normal,
        )

    val bodySfPro: TextStyle
        @Composable get() = TextStyle(
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = sfProRoundedFontFamily(),
            fontWeight = FontWeight.Normal,
        )

    val bodySfUi: TextStyle
        @Composable get() = TextStyle(
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontFamily = sfUiDisplayFontFamily(),
            fontWeight = FontWeight.Medium,
        )

    val bodyLarge: TextStyle
        @Composable get() = TextStyle(
            fontSize = 14.sp,
            lineHeight = 16.sp,
            fontFamily = sfUiDisplayFontFamily(),
            fontWeight = FontWeight.Medium,
        )


}